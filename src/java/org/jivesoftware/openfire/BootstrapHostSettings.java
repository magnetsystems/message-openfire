package org.jivesoftware.openfire;

import java.util.Map;

import org.jivesoftware.util.JiveGlobals;

public class BootstrapHostSettings implements BootstrapSetupStage {
	private BootstrapProperties bootstrapProps;

	public BootstrapHostSettings(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		 xmppSettings.put("xmpp.domain", bootstrapProps.getXmppDomain());
         xmppSettings.put("xmpp.socket.ssl.active", "true");
         xmppSettings.put("xmpp.auth.anonymous", "true");

         xmlSettings.put("adminConsole.port", bootstrapProps.getHttpPort());
         xmlSettings.put("adminConsole.securePort", bootstrapProps.getHttpPortSecure());

         JiveGlobals.setupPropertyEncryptionAlgorithm(bootstrapProps.getEncryptionKey());
		 JiveGlobals.setupPropertyEncryptionKey(bootstrapProps.getEncryptionKey());
	}
}
