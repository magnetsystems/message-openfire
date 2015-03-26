package org.jivesoftware.openfire;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapProfileSettings implements BootstrapSetupStage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapProfileSettings.class);
	private StartupProperties bootstrapProps;

	public BootstrapProfileSettings(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		LOGGER.trace("exec : setting default profile settings");
	
		xmppSettings.put("provider.auth.className",
                org.jivesoftware.openfire.auth.DefaultAuthProvider.class.getName());
        xmppSettings.put("provider.user.className",
                org.jivesoftware.openfire.user.DefaultUserProvider.class.getName());
        xmppSettings.put("provider.group.className",
                org.jivesoftware.openfire.group.DefaultGroupProvider.class.getName());
        xmppSettings.put("provider.vcard.className",
                org.jivesoftware.openfire.vcard.DefaultVCardProvider.class.getName());
        xmppSettings.put("provider.lockout.className",
                org.jivesoftware.openfire.lockout.DefaultLockOutProvider.class.getName());
        xmppSettings.put("provider.securityAudit.className",
                org.jivesoftware.openfire.security.DefaultSecurityAuditProvider.class.getName());
        xmppSettings.put("provider.admin.className",
                org.jivesoftware.openfire.admin.DefaultAdminProvider.class.getName());
        return new BootstrapAdminSettings(bootstrapProps);
	}
}
