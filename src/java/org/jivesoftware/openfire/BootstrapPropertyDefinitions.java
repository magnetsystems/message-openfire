package org.jivesoftware.openfire;

import java.util.ArrayList;
import java.util.List;

public class BootstrapPropertyDefinitions {
	private static final String MYSQL_HOST = "mysql.host";
	private static final String MYSQL_PORT = "mysql.port";
	private static final String MYSQL_USER = "mysql.user";
	private static final String MYSQL_PASSWORD = "mysql.password";
	private static final String MYSQL_DB = "mysql.db";
	private static final String XMPP_DOMAIN = "xmpp.domain";
	private static final String XMPP_ADDR = "xmpp.addr";
	private static final String XMPP_PORT = "xmpp.port";
	private static final String XMPP_SECURE_PORT = "xmpp.secure.port";
	private static final String HTTP_PORT = "http.port";
	private static final String HTTPS_PORT = "https.port"; 
	private static final String LOCALE = "locale";
	private static final String ENCRYPTION = "encryption";
	private static final String ENCRYPTION_KEY = "encryption.key";
	
	private static final List<BootstrapProperty> propertyDefinitions;
	
	static {
		propertyDefinitions = new ArrayList<BootstrapProperty>();
		
		propertyDefinitions.add(new BootstrapProperty(MYSQL_HOST));
		propertyDefinitions.add(new BootstrapProperty(MYSQL_PORT, "3306"));
		propertyDefinitions.add(new BootstrapProperty(MYSQL_USER));
		propertyDefinitions.add(new BootstrapProperty(MYSQL_PASSWORD, ""));
		propertyDefinitions.add(new BootstrapProperty(MYSQL_DB));
		propertyDefinitions.add(new BootstrapProperty(XMPP_DOMAIN, "127.0.0.1"));
		propertyDefinitions.add(new BootstrapProperty(XMPP_ADDR, "127.0.0.1"));
		propertyDefinitions.add(new BootstrapProperty(XMPP_PORT, "5222"));
		propertyDefinitions.add(new BootstrapProperty(XMPP_SECURE_PORT, "5223"));
		propertyDefinitions.add(new BootstrapProperty(HTTP_PORT, "9090"));
		propertyDefinitions.add(new BootstrapProperty(HTTPS_PORT, "9091"));
		propertyDefinitions.add(new BootstrapProperty(LOCALE, "en"));
		propertyDefinitions.add(new BootstrapProperty(ENCRYPTION, null));
		propertyDefinitions.add(new BootstrapProperty(ENCRYPTION_KEY, null));
	}
	
	public static List<BootstrapProperty> getPropertyDefinitions() {
		return propertyDefinitions;
	}
	
}
