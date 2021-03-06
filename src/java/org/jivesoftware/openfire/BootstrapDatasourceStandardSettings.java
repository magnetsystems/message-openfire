package org.jivesoftware.openfire;

import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.database.DefaultConnectionProvider;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class BootstrapDatasourceStandardSettings implements BootstrapSetupStage {

	private static final Logger Log = LoggerFactory.getLogger(BootstrapDatasourceStandardSettings.class);
	private final StartupProperties bootstrapProps;
	private static final String dbServerUrl = "jdbc:mysql://%s:%s/%s?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8mb4&connectionCollation=utf8mb4_unicode_ci";
//	private static final String dbServerUrl = "jdbc:mysql://%s:%s/%s?rewriteBatchedStatements=true&useUnicode=true";

	public BootstrapDatasourceStandardSettings(StartupProperties bootstrapProps) {
		this.bootstrapProps = bootstrapProps;
	}

	@Override
  public BootstrapSetupStage exec(Map<String, String> xmppSettings, Map<String, String> xmlSettings) {
		JiveGlobals.setXMLProperty("connectionProvider.className", "org.jivesoftware.database.DefaultConnectionProvider");
		DefaultConnectionProvider conProvider = new DefaultConnectionProvider();
		try {
			final String driver = "org.mariadb.jdbc.Driver";
			final String dbServerUrl = getServerUrl();
			conProvider.setDriver(driver);
			conProvider.setConnectionTimeout(1.0);
			conProvider.setMinConnections(5);
			conProvider.setMaxConnections(25);
			conProvider.setServerURL(dbServerUrl);
			conProvider.setUsername(bootstrapProps.getDbUser());
			conProvider.setPassword(bootstrapProps.getDbPassword());
			conProvider.setTestSQL(DbConnectionManager.getTestSQL(driver));

			JiveGlobals.setXMLProperty("database.defaultProvider.driver", driver);
			JiveGlobals.setXMLProperty("database.defaultProvider.serverURL", dbServerUrl);
			JiveGlobals.setXMLProperty("database.defaultProvider.username", bootstrapProps.getDbUser());
			JiveGlobals.setXMLProperty("database.defaultProvider.password", bootstrapProps.getDbPassword());
			JiveGlobals.setXMLProperty("database.defaultProvider.testSQL", DbConnectionManager.getTestSQL(driver));

			JiveGlobals.setXMLProperty("database.defaultProvider.minConnections", Integer.toString(5));
			JiveGlobals.setXMLProperty("database.defaultProvider.maxConnections", Integer.toString(25));
			JiveGlobals.setXMLProperty("database.defaultProvider.connectionTimeout", Double.toString(1.0));
		} catch (Exception e) {
			Log.error("Error setting up Database settings for bootstrapProps : {}", bootstrapProps, e);
		}
		DbConnectionManager.setConnectionProvider(conProvider);
		if(testConnection()) {
			Log.debug("DB Connection test succeeded");
		} else {
			Log.error("BootstrapDatasourceStandardSettings.exec() : DB connection test failed");
			throw new RuntimeException("Error connecting to DB bootstrapProps = " + bootstrapProps);
		}
		return new BootstrapProfileSettings(bootstrapProps);
	}

	private String getServerUrl() {
		String url = String.format(dbServerUrl, bootstrapProps.getDbHost(), bootstrapProps.getDbPort(), bootstrapProps.getDbName());
		Log.debug("DB Server url : {}", url);
		return url;
	}

	boolean testConnection() {
		boolean success = true;
		Connection con = null;
		try {
			con = DbConnectionManager.getConnection();
			if (con == null) {
			} else {
				// See if the Jive db schema is installed.
				try {
					Statement stmt = con.createStatement();
					// Pick an arbitrary table to see if it's there.
					stmt.executeQuery("SELECT * FROM ofID");
					stmt.close();
				} catch (SQLException sqle) {
					success = false;
					sqle.printStackTrace();
					Log.error("The Openfire database schema does not appear to be installed", sqle);
				}
			}
		} catch (SQLException ex) {
			success = false;
			Log.error("DB connection failed", ex);
		} finally {
			try {
				con.close();
			} catch (Exception ignored) {

			}
		}
		return success;
	}
}
