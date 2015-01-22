package org.jivesoftware.openfire;

import java.util.Locale;
import java.util.Map;

import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.LocaleUtils;

public class BootstrapLocale implements BootstrapSetupStage {
	private BootstrapProperties bootstrapProps;

	public BootstrapLocale(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void exec(Map<String, String> settings) {
		String localeCode = bootstrapProps.getLocale();
		Locale newLocale = LocaleUtils.localeCodeToLocale(localeCode.trim());
         if (newLocale == null) {
             throw new IllegalArgumentException("Invalid locale code : " + localeCode);
         }
         else {
             JiveGlobals.setLocale(newLocale);
         }
         Locale locale = JiveGlobals.getLocale();
     }
}
