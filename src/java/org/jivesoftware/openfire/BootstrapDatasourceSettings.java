package org.jivesoftware.openfire;

import java.util.Map;

public class BootstrapDatasourceSettings implements BootstrapSetupStage {
	
	private BootstrapProperties bootstrapProps;

	public BootstrapDatasourceSettings(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		
	}
}
