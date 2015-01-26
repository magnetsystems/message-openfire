package org.jivesoftware.openfire;

import java.util.Map;

public interface BootstrapSetupStage {
	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings);
}
