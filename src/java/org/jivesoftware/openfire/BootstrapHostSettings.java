package org.jivesoftware.openfire;

import java.util.Map;

import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapHostSettings implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapHostSettings.class);
	private StartupProperties bootstrapProps;

	public BootstrapHostSettings(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		 LOGGER.trace("exec : setting server properties");
		 xmppSettings.put("xmpp.domain", bootstrapProps.getXmppDomain());
		 xmppSettings.put("xmpp.socket.plain.port", bootstrapProps.getXmppPort());
		 xmppSettings.put("xmpp.socket.ssl.port", bootstrapProps.getXmppPortSecure());
         xmppSettings.put("xmpp.socket.ssl.active", "true");
         xmppSettings.put("xmpp.auth.anonymous", "true");
         xmppSettings.put("mmx.admin.api.port", bootstrapProps.getMmxAdminPort());
         xmppSettings.put("mmx.admin.api.https.port", bootstrapProps.getMmxAdminPortSecure());
         xmppSettings.put("mmx.rest.http.port", bootstrapProps.getMmxPublicPort());
         xmppSettings.put("mmx.rest.https.port", bootstrapProps.getMmxPublicPortSecure());
         xmlSettings.put("adminConsole.port", bootstrapProps.getHttpPort());
         xmlSettings.put("adminConsole.securePort", bootstrapProps.getHttpPortSecure());
         if("true".equalsIgnoreCase(bootstrapProps.getStandaloneServer())) {
        	 LOGGER.debug("exec : setting socket linger time props to 0");
        	 xmlSettings.put("xmpp.socket.linger", "0");
        	 xmlSettings.put("http.socket.linger", "0");
         }
         JiveGlobals.setupPropertyEncryptionAlgorithm(bootstrapProps.getEncryptionKey());
		 JiveGlobals.setupPropertyEncryptionKey(bootstrapProps.getEncryptionKey());
		 
		 return new BootstrapDatasourceSettings(bootstrapProps);
	}
}
