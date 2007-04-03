/**
 * $RCSfile$
 * $Revision$
 * $Date$
 *
 * Copyright (C) 2004 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Public License (GPL),
 * a copy of which is included in this distribution.
 */

package org.jivesoftware.messenger.net;

import org.dom4j.io.XMLWriter;
import org.jivesoftware.messenger.*;
import org.jivesoftware.messenger.auth.UnauthorizedException;
import org.jivesoftware.messenger.interceptor.InterceptorManager;
import org.jivesoftware.messenger.interceptor.PacketRejectedException;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.Log;
import org.xmpp.packet.Packet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * An object to track the state of a XMPP client-server session.
 * Currently this class contains the socket channel connecting the
 * client and server.
 *
 * @author Iain Shigeoka
 */
public class SocketConnection implements Connection {

    private Map listeners = new HashMap();

    private Socket socket;

    /**
     * The utf-8 charset for decoding and encoding XMPP packet streams.
     */
    private String charset = "UTF-8";

    private Writer writer;

    private PacketDeliverer deliverer;

    private Session session;
    private boolean secure;
    private XMLWriter xmlSerializer;
    private boolean flashClient = false;
    private int majorVersion = 1;
    private int minorVersion = 0;
    private String language = null;

    /**
     * Create a new session using the supplied socket.
     *
     * @param deliverer the packet deliverer this connection will use.
     * @param socket the socket to represent.
     * @param isSecure true if this is a secure connection.
     * @throws NullPointerException if the socket is null.
     */
    public SocketConnection(PacketDeliverer deliverer, Socket socket, boolean isSecure)
            throws IOException
    {
        if (socket == null) {
            throw new NullPointerException("Socket channel must be non-null");
        }

        this.secure = isSecure;
        this.socket = socket;
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charset));
        this.deliverer = deliverer;
        xmlSerializer = new XMLWriter(writer);
    }

    public boolean validate() {
        if (isClosed()) {
            return false;
        }
        try {
            synchronized (writer) {
                writer.write(" ");
                writer.flush();
            }
        }
        catch (Exception e) {
            Log.warn("Closing no longer valid connection", e);
            close();
        }
        return !isClosed();
    }

    public void init(Session owner) {
        session = owner;
    }

    public Object registerCloseListener(ConnectionCloseListener listener, Object handbackMessage) {
        Object status = null;
        if (isClosed()) {
            listener.onConnectionClose(handbackMessage);
        }
        else {
            status = listeners.put(listener, handbackMessage);
        }
        return status;
    }

    public Object removeCloseListener(ConnectionCloseListener listener) {
        return listeners.remove(listener);
    }

    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    public Writer getWriter() {
        return writer;
    }

    public boolean isClosed() {
        if (session == null) {
            return socket.isClosed();
        }
        return session.getStatus() == Session.STATUS_CLOSED;
    }

    public boolean isSecure() {
        return secure;
    }

    public int getMajorXMPPVersion() {
        return majorVersion;
    }

    public int getMinorXMPPVersion() {
        return minorVersion;
    }

    /**
     * Sets the XMPP version information. In most cases, the version should be "1.0".
     * However, older clients using the "Jabber" protocol do not set a version. In that
     * case, the version is "0.0".
     *
     * @param majorVersion the major version.
     * @param minorVersion the minor version.
     */
    public void setXMPPVersion(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language code that should be used for this connection (e.g. "en").
     *
     * @param language the language code.
     */
    public void setLanaguage(String language) {
        this.language = language;
    }

    public boolean isFlashClient() {
        return flashClient;
    }

    /**
     * Sets whether the connected client is a flash client. Flash clients need to
     * receive a special character (i.e. \0) at the end of each xml packet. Flash
     * clients may send the character \0 in incoming packets and may start a
     * connection using another openning tag such as: "flash:client".
     *
     * @param flashClient true if the if the connection is a flash client.
     */
    public void setFlashClient(boolean flashClient) {
        this.flashClient = flashClient;
    }

    public synchronized void close() {
        if (!isClosed()) {
            try {
                if (session != null) {
                    session.setStatus(Session.STATUS_CLOSED);
                }
                synchronized (writer) {
                    try {
                        writer.write("</stream:stream>");
                        if (flashClient) {
                            writer.write('\0');
                        }
                        xmlSerializer.flush();
                    }
                    catch (IOException e) {}
                }
            }
            catch (Exception e) {
                // Do nothing
            }
            try {
                socket.close();
            }
            catch (Exception e) {
                Log.error(LocaleUtils.getLocalizedString("admin.error.close")
                        + "\n" + this.toString(), e);
            }
            notifyCloseListeners();
        }
    }

    public void deliver(Packet packet) throws UnauthorizedException, PacketException {
        if (isClosed()) {
            deliverer.deliver(packet);
        }
        else {
            try {
                // Invoke the interceptors before we send the packet
                InterceptorManager.getInstance().invokeInterceptors(packet, session, false, false);
                synchronized (writer) {
                    try {
                        xmlSerializer.write(packet.getElement());
                        if (flashClient) {
                            writer.write('\0');
                        }
                        xmlSerializer.flush();
                    }
                    catch (IOException e) {
                        Log.warn(e);
                        close();
                        // Retry sending the packet again. Most probably if the packet is a
                        // Message it will be stored offline
                        deliverer.deliver(packet);
                    }
                }
                // Invoke the interceptors after we have sent the packet
                InterceptorManager.getInstance().invokeInterceptors(packet, session, false, true);
                session.incrementServerPacketCount();
            }
            catch (PacketRejectedException e) {
                // An interceptor rejected the packet so do nothing
            }
        }
    }

    /**
     * Notifies all close listeners that the connection has been closed.
     * Used by subclasses to properly finish closing the connection.
     */
    private void notifyCloseListeners() {
        synchronized (listeners) {
            Iterator itr = listeners.keySet().iterator();
            while (itr.hasNext()) {
                ConnectionCloseListener listener = (ConnectionCloseListener)itr.next();
                listener.onConnectionClose(listeners.get(listener));
            }
        }
    }
}