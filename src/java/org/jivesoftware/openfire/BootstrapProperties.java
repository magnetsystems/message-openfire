package org.jivesoftware.openfire;

/**
 * @author sdatar
 * 
 * If defining a new property my.foo.bar. in bootstrap.properties, you need to add a private 
 * member string named myfooBar;
 * 
 *
 */

public class BootstrapProperties {
	private String mysqlHost;
	private String mysqlPort;
	private String mysqlDb;
	private String mysqlUser;
	private String mysqlPassword;
	private String xmppDomain;
	private String xmppPort;
	private String xmppAddr;
	private String xmppPortSecure;
	private String xmppSecurePort;
	private String encryptionKey;
	private String encryptionPort;
	private String httpPort;
	private String httpPortSecure;
	private String locale;

	public String getMysqlHost() {
		return mysqlHost;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}

	public String getMysqlPort() {
		return mysqlPort;
	}

	public void setMysqlPort(String mysqlPort) {
		this.mysqlPort = mysqlPort;
	}

	public String getMysqlDb() {
		return mysqlDb;
	}

	public void setMysqlDb(String mysqlDb) {
		this.mysqlDb = mysqlDb;
	}

	public String getMysqlUser() {
		return mysqlUser;
	}

	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}

	public String getXmppDomain() {
		return xmppDomain;
	}

	public void setXmppDomain(String xmppDomain) {
		this.xmppDomain = xmppDomain;
	}
	
	public String getXmppPort() {
		return xmppPort;
	}

	public void setXmppPort(String xmppPort) {
		this.xmppPort = xmppPort;
	}

	public String getXmppAddr() {
		return xmppAddr;
	}

	public void setXmppAddr(String xmppAddr) {
		this.xmppAddr = xmppAddr;
	}

	public String getXmppPortSecure() {
		return xmppPortSecure;
	}

	public void setXmppPortSecure(String xmppPortSecure) {
		this.xmppPortSecure = xmppPortSecure;
	}

	public String getXmppSecurePort() {
		return xmppSecurePort;
	}

	public void setXmppSecurePort(String xmppSecurePort) {
		this.xmppSecurePort = xmppSecurePort;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getEncryptionPort() {
		return encryptionPort;
	}

	public void setEncryptionPort(String encryptionPort) {
		this.encryptionPort = encryptionPort;
	}

	public String getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(String httpPort) {
		this.httpPort = httpPort;
	}
	
	public String getHttpPortSecure() {
		return httpPortSecure;
	}

	public void setHttpPortSecure(String httpPortSecure) {
		this.httpPortSecure = httpPortSecure;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "BootstrapProperties [mysqlHost=" + mysqlHost + ", mysqlPort=" + mysqlPort + ", mysqlDb=" + mysqlDb + ", mysqlUser=" + mysqlUser
				+ ", mysqlPassword=" + mysqlPassword + ", xmppDomain=" + xmppDomain + ", xmppPort=" + xmppPort + ", xmppAddress=" + xmppAddress
				+ ", xmppPortSecure=" + xmppPortSecure + ", xmppSecurePort=" + xmppSecurePort + ", encryptionKey=" + encryptionKey + ", encryptionPort="
				+ encryptionPort + ", httpPort=" + httpPort + ", httpPortSecure=" + httpPortSecure + ", locale=" + locale + "]";
	}
	
}
