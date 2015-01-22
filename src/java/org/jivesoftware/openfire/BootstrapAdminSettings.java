package org.jivesoftware.openfire;

import java.util.Date;
import java.util.Map;

import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveGlobals;

public class BootstrapAdminSettings implements BootstrapSetupStage {
	private BootstrapProperties bootstrapProps;

	public BootstrapAdminSettings(BootstrapProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	public void exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
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
	}
}
