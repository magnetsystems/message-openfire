package org.jivesoftware.openfire;

import java.util.Map;

import org.jivesoftware.util.JiveGlobals;

public class BootstrapSetupFinished implements BootstrapSetupStage {
	private BootstrapProperties bootstrapProps;

	public BootstrapSetupFinished(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
	    for (String name : xmppSettings.keySet()) {
	        String value = xmppSettings.get(name);
	        JiveGlobals.setProperty(name, value);
	    }
	    for (String name : xmlSettings.keySet()) {
	        String value = xmlSettings.get(name);
	        JiveGlobals.setXMLProperty(name, value);
	    }
	    XMPPServer.getInstance().finishSetup();
	}
}
