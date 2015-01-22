package org.jivesoftware.openfire;

import java.beans.Statement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BootstrapPropertyParser {
	private Properties fileProperties;

	public BootstrapPropertyParser(Properties fileProperties) {
		this.fileProperties = fileProperties;
	}

	public BootstrapProperties getProperties() throws Exception {
		BootstrapProperties properties = new BootstrapProperties();
		List<BootstrapProperty> propertyList = BootstrapPropertyDefinitions.getPropertyDefinitions();
		for (BootstrapProperty property : propertyList) {
			String value = fileProperties.getProperty(property.getName());
			if (isNullOrEmpty(value) && property.isMandatory()) {
				throw new IllegalArgumentException("Property : " + property.getName() + " is Mandatory, please set a value in bootstrap.properties");
			}
			Statement s = new Statement(properties, "set" + getMemberNameFromProperty(property.getName()), new Object[]{value});
		    s.execute();
		}
		return properties;
	}

	public boolean isNullOrEmpty(String s) {
		if (s == null || s.isEmpty())
			return true;
		return false;
	}

	public String getMemberNameFromProperty(String propertyName) {
		StringBuffer sb = new StringBuffer(100);
		boolean lastCharDot = false;
		for (int i = 0; i < propertyName.length(); i++) {
			char c = propertyName.charAt(i);
			if (i == 0)
				sb.append(Character.toUpperCase(c));
			else if (c == '.')
				lastCharDot = true;
			else if (lastCharDot) {
				sb.append(Character.toUpperCase(c));
				lastCharDot = false;
			} else
				sb.append(c);
		}
		return sb.toString();
	}
}
