package org.jivesoftware.openfire;

import java.beans.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sdatar
 * 
 * If defining a new property my.foo.bar. in bootstrap.properties, you need to add a private 
 * member string named myfooBar;
 * 
 *
 */

public class StartupProperties {
	private static final Logger Log = LoggerFactory.getLogger(StartupProperties.class);
	
	private static final String DB_HOST = "dbHost";
	private static final String DB_PORT = "dbPort";
	private static final String DB_USER = "dbUser";
	private static final String DB_PASSWORD = "dbPassword";
	private static final String DB_NAME = "dbName";
	private static final String XMPP_DOMAIN = "xmppDomain";
	private static final String XMPP_PORT = "xmppPort";
	private static final String XMPP_PORT_SECURE = "xmppPortSecure";
	private static final String HTTP_PORT = "httpPort";
	private static final String HTTP_PORT_SECURE = "httpPortSecure"; 
	private static final String LOCALE = "locale";
	private static final String ENCRYPTION = "encryption";
	private static final String ENCRYPTION_KEY = "encryptionKey";
	private static final String MMX_ADMIN_PORT = "mmxAdminPort";
	private static final String MMX_ADMIN_PORT_SECURE = "mmxAdminPortSecure";
	private static final String MMX_PUBLIC_PORT = "mmxPublicPort";
	private static final String MMX_PUBLIC_PORT_SECURE = "mmxPublicPortSecure";
	
	private static List<String> propertyList = Arrays.asList(DB_HOST, DB_PORT, 
													  DB_USER, DB_PASSWORD, 
													  DB_NAME, XMPP_DOMAIN, 
													  XMPP_PORT, XMPP_PORT_SECURE,
													  HTTP_PORT, HTTP_PORT_SECURE,
													  LOCALE, ENCRYPTION, ENCRYPTION_KEY, 
													  MMX_ADMIN_PORT, MMX_ADMIN_PORT_SECURE, 
													  MMX_PUBLIC_PORT, MMX_PUBLIC_PORT_SECURE);
	
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	private String xmppDomain;
	private String xmppPort;
	private String xmppPortSecure;
	private String encryptionKey = null;
	private String encryption = null;
	private String httpPort;
	private String httpPortSecure;
	private String locale = "EN";
	private String mmxAdminPort;
	private String mmxAdminPortSecure;
	private String mmxPublicPort;
	private String mmxPublicPortSecure;
	
	private boolean isBootstrappable = true;
	
	
	public static StartupProperties parseAndGet(Properties fileProperties) {
		StartupProperties properties = new StartupProperties();
		for (String property : propertyList) {
			String value = fileProperties.getProperty(property);	
			if(!isNullOrEmpty(value)) {
				Statement s = new Statement(properties, "set" + capitalizeFirstChar(property), new Object[]{value});
				try {
					s.execute();
				} catch (Exception e) {
					Log.error("Caught exception setting property : {}, value={}", new Object[]{property, value, e});
					if(isMandatory(property)) {
						Log.debug("Property : {} is mandatory but is not set", property);
						properties.setBootstrappable(false);
					}
				}
		    } else {
		    	if(isMandatory(property)) {
		    		Log.debug("Property : {} is mandatory but is not set", property);
		    		properties.setBootstrappable(false);
		    	}
		    }
		}
		return properties;
	}
	
	public String getDbHost() {
		return dbHost;
	}
	
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getXmppDomain() {
		return xmppDomain;
	}
	public void setXmppDomain(String xmppDomain) {
		this.xmppDomain = xmppDomain;
	}
	public String getXmppPort() {
		return xmppPort;
	}
	public void setXmppPort(String xmppPort) {
		this.xmppPort = xmppPort;
	}
	
	public String getXmppPortSecure() {
		return xmppPortSecure;
	}
	public void setXmppPortSecure(String xmppPortSecure) {
		this.xmppPortSecure = xmppPortSecure;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	public String getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(String httpPort) {
		this.httpPort = httpPort;
	}
	public String getHttpPortSecure() {
		return httpPortSecure;
	}
	public void setHttpPortSecure(String httpsPort) {
		this.httpPortSecure = httpsPort;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getMmxAdminPort() {
		return mmxAdminPort;
	}

	public void setMmxAdminPort(String mmxAdminPort) {
		this.mmxAdminPort = mmxAdminPort;
	}

	public String getMmxAdminPortSecure() {
		return mmxAdminPortSecure;
	}

	public void setMmxAdminPortSecure(String mmxAdminPortSecure) {
		this.mmxAdminPortSecure = mmxAdminPortSecure;
	}

	public String getMmxPublicPort() {
		return mmxPublicPort;
	}

	public void setMmxPublicPort(String mmxPublicPort) {
		this.mmxPublicPort = mmxPublicPort;
	}

	public String getMmxPublicPortSecure() {
		return mmxPublicPortSecure;
	}

	public void setMmxPublicPortSecure(String mmxPublicPortSecure) {
		this.mmxPublicPortSecure = mmxPublicPortSecure;
	}

	public boolean isBootstrappable() {
		return isBootstrappable;
	}
	
	private void setBootstrappable(boolean isBootstrappable) {
		this.isBootstrappable = isBootstrappable;
	}
	
	@Override
	public String toString() {
		return "BootstrapProperties [dbHost=" + dbHost + ", dbPort=" + dbPort + ", dbDb=" + dbName + ", dbUser=" + dbUser
				+ ", dbPassword=" + dbPassword + ", xmppDomain=" + xmppDomain + ", xmppPort=" + xmppPort + ", xmppPortSecure="
				+ xmppPortSecure + ", encryptionKey=" + encryptionKey + ", encryption=" + encryption + ", httpPort="
				+ httpPort + ", httpPortSecure=" + httpPortSecure + ", locale=" + locale + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encryption == null) ? 0 : encryption.hashCode());
		result = prime * result + ((encryptionKey == null) ? 0 : encryptionKey.hashCode());
		result = prime * result + ((httpPort == null) ? 0 : httpPort.hashCode());
		result = prime * result + ((httpPortSecure == null) ? 0 : httpPortSecure.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = prime * result + ((dbHost == null) ? 0 : dbHost.hashCode());
		result = prime * result + ((dbPassword == null) ? 0 : dbPassword.hashCode());
		result = prime * result + ((dbPort == null) ? 0 : dbPort.hashCode());
		result = prime * result + ((dbUser == null) ? 0 : dbUser.hashCode());
		result = prime * result + ((xmppDomain == null) ? 0 : xmppDomain.hashCode());
		result = prime * result + ((xmppPort == null) ? 0 : xmppPort.hashCode());
		result = prime * result + ((xmppPortSecure == null) ? 0 : xmppPortSecure.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StartupProperties other = (StartupProperties) obj;
		if (encryption == null) {
			if (other.encryption != null)
				return false;
		} else if (!encryption.equals(other.encryption))
			return false;
		if (encryptionKey == null) {
			if (other.encryptionKey != null)
				return false;
		} else if (!encryptionKey.equals(other.encryptionKey))
			return false;
		if (httpPort == null) {
			if (other.httpPort != null)
				return false;
		} else if (!httpPort.equals(other.httpPort))
			return false;
		if (httpPortSecure == null) {
			if (other.httpPortSecure != null)
				return false;
		} else if (!httpPortSecure.equals(other.httpPortSecure))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (dbHost == null) {
			if (other.dbHost != null)
				return false;
		} else if (!dbHost.equals(other.dbHost))
			return false;
		if (dbPassword == null) {
			if (other.dbPassword != null)
				return false;
		} else if (!dbPassword.equals(other.dbPassword))
			return false;
		if (dbPort == null) {
			if (other.dbPort != null)
				return false;
		} else if (!dbPort.equals(other.dbPort))
			return false;
		if (dbUser == null) {
			if (other.dbUser != null)
				return false;
		} else if (!dbUser.equals(other.dbUser))
			return false;
		if (xmppDomain == null) {
			if (other.xmppDomain != null)
				return false;
		} else if (!xmppDomain.equals(other.xmppDomain))
			return false;
		if (xmppPort == null) {
			if (other.xmppPort != null)
				return false;
		} else if (!xmppPort.equals(other.xmppPort))
			return false;
		if (xmppPortSecure == null) {
			if (other.xmppPortSecure != null)
				return false;
		} else if (!xmppPortSecure.equals(other.xmppPortSecure))
			return false;
		return true;
	}
	
	private static boolean isNullOrEmpty(String s) {
		return s==null || s.isEmpty();
	}
	
	private static boolean isMandatory(String property) {
		return !ENCRYPTION.equals(property) && !ENCRYPTION_KEY.equals(property) &&
			   !DB_PASSWORD.equals(property) && !LOCALE.equals(property);	
	}
	
	private static String capitalizeFirstChar(String property) {
		return Character.toString(property.charAt(0)).toUpperCase()+property.substring(1);
	}
}
