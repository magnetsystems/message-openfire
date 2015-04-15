package org.jivesoftware.openfire;

import java.util.Locale;
import java.util.Map;

import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapLocale implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapLocale.class);
	private StartupProperties bootstrapProps;

	public BootstrapLocale(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		String localeCode = bootstrapProps.getLocale();
		LOGGER.trace("exec : setting locale {}", localeCode);
		Locale newLocale = LocaleUtils.localeCodeToLocale(localeCode.trim());
         if (newLocale == null) {
        	 LOGGER.error("BootstrapLocale.exec : Locale is not set");
        	 throw new IllegalArgumentException("Invalid locale code : " + localeCode);
         }
         else {
             JiveGlobals.setLocale(newLocale);
         }
         Locale locale = JiveGlobals.getLocale();
         return new BootstrapHostSettings(bootstrapProps);
     }
}
