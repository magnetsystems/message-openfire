package org.jivesoftware.openfire;

import java.util.Map;

import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapSetupFinished implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapSetupFinished.class);
	private StartupProperties bootstrapProps;

	public BootstrapSetupFinished(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
	    LOGGER.trace("exec : finishing setup");
	    
		for (String name : xmppSettings.keySet()) {
	        String value = xmppSettings.get(name);
	        JiveGlobals.setProperty(name, value);
	    }
	    for (String name : xmlSettings.keySet()) {
	        String value = xmlSettings.get(name);
	        JiveGlobals.setXMLProperty(name, value);
	    }
	    XMPPServer.getInstance().finishSetup();
	    return null;
	}
}
