package org.jivesoftware.openfire;

import java.util.Map;

import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapHostSettings implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapHostSettings.class);
	private BootstrapProperties bootstrapProps;

	public BootstrapHostSettings(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		 LOGGER.trace("exec : setting server properties");
		 xmppSettings.put("xmpp.domain", bootstrapProps.getXmppDomain());
		 xmppSettings.put("xmpp.socket.plain.port", bootstrapProps.getXmppPort());
		 xmppSettings.put("xmpp.socket.ssl.port", bootstrapProps.getXmppSecurePort());
         xmppSettings.put("xmpp.socket.ssl.active", "true");
         xmppSettings.put("xmpp.auth.anonymous", "true");
         
         xmlSettings.put("adminConsole.port", bootstrapProps.getHttpPort());
         xmlSettings.put("adminConsole.securePort", bootstrapProps.getHttpsPort());

         JiveGlobals.setupPropertyEncryptionAlgorithm(bootstrapProps.getEncryptionKey());
		 JiveGlobals.setupPropertyEncryptionKey(bootstrapProps.getEncryptionKey());
		 
		 return new BootstrapDatasourceSettings(bootstrapProps);
	}
}
