package org.jivesoftware.openfire.container;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.openfire.BootstrapProcessor;
import org.jivesoftware.openfire.BootstrapProperties;
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
			BootstrapProperties bootsrapProperties = gson.fromJson(reader, BootstrapProperties.class);
			Log.debug("doPost : bootsrapProperties={}", bootsrapProperties);
			BootstrapProcessor bootstrapProcessor = new BootstrapProcessor(bootsrapProperties);
			bootstrapProcessor.bootstrap();
			response.setStatus(HttpServletResponse.SC_CREATED);
			return;
		}
		Log.debug("doPost : Server is already setup, ignoring...");
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
