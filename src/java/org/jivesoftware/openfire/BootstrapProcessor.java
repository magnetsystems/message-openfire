package org.jivesoftware.openfire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapProcessor {
	private static final Logger Log = LoggerFactory.getLogger(BootstrapProcessor.class);
	
	private BootstrapProperties bootstrapProps;

	public BootstrapProcessor(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public boolean bootstrap() {
		Log.debug("bootstrap : bootstrapProps={}", bootstrapProps);
		try {
			Map<String, String> xmppSettings = new HashMap<String, String>();
			Map<String, String> xmlSettings = new HashMap<String, String>();
			BootstrapSetupStage stage = new BootstrapLocale(bootstrapProps);
			while(stage != null) {
				stage = stage.exec(xmppSettings, xmlSettings);
			}
		} catch (Exception e) {
			Log.error("bootstrap : bootstrapPros={}", bootstrapProps, e);
			e.printStackTrace();
			return false;
		}
		return true;
	}	
}
