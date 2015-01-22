package org.jivesoftware.openfire;

import java.util.Map;

public interface BootstrapSetupStage {
	public void exec(Map<String, String> settings);
}
