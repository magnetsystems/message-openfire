package org.jivesoftware.openfire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BootstrapProcessor {
	private BootstrapProperties bootstrapProps;

	public BootstrapProcessor(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void bootstrap() {
		Map<String, String> xmppSettings = new HashMap<String, String>();
		Map<String, String> xmlSettings = new HashMap<String, String>();
		BootstrapSetupStage stage = new BootstrapLocale(bootstrapProps);
		while(stage != null) {
			stage = stage.exec(xmppSettings, xmlSettings);
		}
	}	
}
