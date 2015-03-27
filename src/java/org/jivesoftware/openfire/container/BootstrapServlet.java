package org.jivesoftware.openfire.container;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.openfire.BootstrapProcessor;
import org.jivesoftware.openfire.StartupProperties;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class BootstrapServlet extends HttpServlet {
    
	private static final Logger Log = LoggerFactory.getLogger(BootstrapServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!"true".equals(JiveGlobals.getXMLProperty("setup"))) {
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			StartupProperties bootstrapProperties = gson.fromJson(reader, StartupProperties.class);
			
			String dbHost = bootstrapProperties.getDbHost();
			String dbPort = bootstrapProperties.getDbPort();
			String dbUser = bootstrapProperties.getDbUser();
			String dbPassword = bootstrapProperties.getDbPassword();
			String dbName = bootstrapProperties.getDbName();
			
			Log.debug("Received DB properties : dbHost={}, dpPort={}, dbUser={}, dbName={}", 
					  new Object[]{dbHost, dbPort, dbUser, dbHost});
			
			if(isNullOrEmpty(dbHost) || 
			   isNullOrEmpty(dbPort) || 
			   isNullOrEmpty(dbUser) || 
			   isNullOrEmpty(dbName))  {
				response.setContentType("text/plain");
				response.getWriter().write("Invalid DB properties");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
				
			StartupProperties startupProperties = XMPPServer.getInstance().getStartupProperties();
			
			startupProperties.setDbHost(bootstrapProperties.getDbHost());
			startupProperties.setDbPort(bootstrapProperties.getDbPort());
			startupProperties.setDbUser(bootstrapProperties.getDbUser());
			if(dbPassword == null) dbPassword = "";	
			startupProperties.setDbPassword(bootstrapProperties.getDbPassword());
			startupProperties.setDbName(bootstrapProperties.getDbName());
			
			Log.debug("doPost : bootsrapProperties={}", startupProperties);
			BootstrapProcessor bootstrapProcessor = new BootstrapProcessor(startupProperties);
			bootstrapProcessor.bootstrap();
			response.setStatus(HttpServletResponse.SC_CREATED);
			return;
		}
		Log.debug("doPost : Server is already setup, ignoring...");
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		String jsonString = null;
		if("true".equals(JiveGlobals.getXMLProperty("setup"))) {
			BootstrapInfo info = new BootstrapInfo(); 
			info.setSetupComplete(true);
			info.setMmxAdminPort(JiveGlobals.getProperty("mmx.admin.api.port"));
			info.setMmxAdminPortSecure(JiveGlobals.getProperty("mmx.admin.api.https.port"));
			info.setMmxPublicPort(JiveGlobals.getProperty("mmx.rest.http.port"));
			info.setMmxPublicPortSecure(JiveGlobals.getProperty("mmx.rest.https.port"));
			jsonString = gson.toJson(info);
		} else {
			BootstrapInfo info = new BootstrapInfo();
			info.setSetupComplete(false);
			jsonString = gson.toJson(info);		
		}
		Log.debug("doGet : Returning setupStatus : {}", jsonString);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	public static class BootstrapInfo {
	  	private boolean setupComplete;

	  	private String mmxAdminPort;
	  	private String mmxAdminPortSecure;
	  	private String mmxPublicPort;
	  	private String mmxPublicPortSecure;
		public boolean isSetupComplete() {
			return setupComplete;
		}
		public void setSetupComplete(boolean setupComplete) {
			this.setupComplete = setupComplete;
		}
		public String getMmxAdminPort() {
			return mmxAdminPort;
		}
		public void setMmxAdminPort(String mmxAdminPort) {
			this.mmxAdminPort = mmxAdminPort;
		}
		public String getMmxAdminPortSecure() {
			return mmxAdminPortSecure;
		}
		public void setMmxAdminPortSecure(String mmxAdminPortSecure) {
			this.mmxAdminPortSecure = mmxAdminPortSecure;
		}
		public String getMmxPublicPort() {
			return mmxPublicPort;
		}
		public void setMmxPublicPort(String mmxPublicPort) {
			this.mmxPublicPort = mmxPublicPort;
		}
		public String getMmxPublicPortSecure() {
			return mmxPublicPortSecure;
		}
		public void setMmxPublicPortSecure(String mmxPublicPortSecure) {
			this.mmxPublicPortSecure = mmxPublicPortSecure;
		}
	}
	
	private boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}
}
