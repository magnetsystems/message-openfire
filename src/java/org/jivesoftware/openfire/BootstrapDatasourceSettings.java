package org.jivesoftware.openfire;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapDatasourceSettings implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapDatasourceSettings.class);
	private StartupProperties bootstrapProps;

	public BootstrapDatasourceSettings(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		LOGGER.trace("exec : ");
		return new BootstrapDatasourceStandardSettings(bootstrapProps);
	}
}
