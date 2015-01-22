package org.jivesoftware.openfire;

public class BootstrapProperty {
	
	private String name;
	private String defaultValue;
	private boolean mandatory;
	
	/**
	 * For non-mandatory properites
	 * 
	 * @param name Property name
	 * @param defaultValue default value of the property
	 */
	public BootstrapProperty(String name, String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.mandatory = false;
	}

	/**
	 * For mandatory properties 
	 * 
	 * @param name Property name
	 */
	public BootstrapProperty(String name) {
		this.name = name;
		this.mandatory = true;
	}
	
	public String getName() {
		return name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isMandatory() {
		return mandatory;
	}	
}
