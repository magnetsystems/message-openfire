package org.jivesoftware.openfire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapProcessor {
	private static final Logger Log = LoggerFactory.getLogger(BootstrapProcessor.class);
	
	private StartupProperties startupProps;

	public BootstrapProcessor(StartupProperties bootstrapProps) {
		this.startupProps = bootstrapProps;
	}

	public boolean bootstrap() {
		Log.debug("bootstrap : startupProps={}", startupProps);
		try {
			Map<String, String> xmppSettings = new HashMap<String, String>();
			Map<String, String> xmlSettings = new HashMap<String, String>();
			BootstrapSetupStage stage = new BootstrapLocale(startupProps);
			while(stage != null) {
				stage = stage.exec(xmppSettings, xmlSettings);
			}
		} catch (Exception e) {
			Log.error("bootstrap : startupProps={}", startupProps, e);
			e.printStackTrace();
			return false;
		}
		return true;
	}	
}
