package org.jivesoftware.openfire;

import java.util.Date;
import java.util.Map;

import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapAdminSettings implements BootstrapSetupStage {
	
	private static final Logger Log = LoggerFactory.getLogger(BootstrapAdminSettings.class);
	private StartupProperties bootstrapProps;

	public BootstrapAdminSettings(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		Log.trace("exec : add admin user");
		User adminUser;
		try {
			adminUser = UserManager.getInstance().getUser("admin");
			adminUser.setPassword("admin");
		    Date now = new Date();
		    adminUser.setCreationDate(now);
		    adminUser.setModificationDate(now);
		    JiveGlobals.setXMLProperty("setup","true");
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return new BootstrapSetupFinished(bootstrapProps);
	}
}
