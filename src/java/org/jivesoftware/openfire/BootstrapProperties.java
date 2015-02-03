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
	private String xmppDomain = "127.0.0.1";
	private String xmppPort = "5222";
	private String xmppAddr = "127.0.0.1";
	private String xmppPortSecure = "5223";
	private String xmppSecurePort = "5223";
	private String encryptionKey = null;
	private String encryption = null;
	private String httpPort = "9090";
	private String httpsPort = "9091";
	private String locale = "EN";
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
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	public String getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(String httpPort) {
		this.httpPort = httpPort;
	}
	public String getHttpsPort() {
		return httpsPort;
	}
	public void setHttpsPort(String httpsPort) {
		this.httpsPort = httpsPort;
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
				+ ", mysqlPassword=" + mysqlPassword + ", xmppDomain=" + xmppDomain + ", xmppPort=" + xmppPort + ", xmppAddr=" + xmppAddr + ", xmppPortSecure="
				+ xmppPortSecure + ", xmppSecurePort=" + xmppSecurePort + ", encryptionKey=" + encryptionKey + ", encryption=" + encryption + ", httpPort="
				+ httpPort + ", httpsPort=" + httpsPort + ", locale=" + locale + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encryption == null) ? 0 : encryption.hashCode());
		result = prime * result + ((encryptionKey == null) ? 0 : encryptionKey.hashCode());
		result = prime * result + ((httpPort == null) ? 0 : httpPort.hashCode());
		result = prime * result + ((httpsPort == null) ? 0 : httpsPort.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((mysqlDb == null) ? 0 : mysqlDb.hashCode());
		result = prime * result + ((mysqlHost == null) ? 0 : mysqlHost.hashCode());
		result = prime * result + ((mysqlPassword == null) ? 0 : mysqlPassword.hashCode());
		result = prime * result + ((mysqlPort == null) ? 0 : mysqlPort.hashCode());
		result = prime * result + ((mysqlUser == null) ? 0 : mysqlUser.hashCode());
		result = prime * result + ((xmppAddr == null) ? 0 : xmppAddr.hashCode());
		result = prime * result + ((xmppDomain == null) ? 0 : xmppDomain.hashCode());
		result = prime * result + ((xmppPort == null) ? 0 : xmppPort.hashCode());
		result = prime * result + ((xmppPortSecure == null) ? 0 : xmppPortSecure.hashCode());
		result = prime * result + ((xmppSecurePort == null) ? 0 : xmppSecurePort.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BootstrapProperties other = (BootstrapProperties) obj;
		if (encryption == null) {
			if (other.encryption != null)
				return false;
		} else if (!encryption.equals(other.encryption))
			return false;
		if (encryptionKey == null) {
			if (other.encryptionKey != null)
				return false;
		} else if (!encryptionKey.equals(other.encryptionKey))
			return false;
		if (httpPort == null) {
			if (other.httpPort != null)
				return false;
		} else if (!httpPort.equals(other.httpPort))
			return false;
		if (httpsPort == null) {
			if (other.httpsPort != null)
				return false;
		} else if (!httpsPort.equals(other.httpsPort))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (mysqlDb == null) {
			if (other.mysqlDb != null)
				return false;
		} else if (!mysqlDb.equals(other.mysqlDb))
			return false;
		if (mysqlHost == null) {
			if (other.mysqlHost != null)
				return false;
		} else if (!mysqlHost.equals(other.mysqlHost))
			return false;
		if (mysqlPassword == null) {
			if (other.mysqlPassword != null)
				return false;
		} else if (!mysqlPassword.equals(other.mysqlPassword))
			return false;
		if (mysqlPort == null) {
			if (other.mysqlPort != null)
				return false;
		} else if (!mysqlPort.equals(other.mysqlPort))
			return false;
		if (mysqlUser == null) {
			if (other.mysqlUser != null)
				return false;
		} else if (!mysqlUser.equals(other.mysqlUser))
			return false;
		if (xmppAddr == null) {
			if (other.xmppAddr != null)
				return false;
		} else if (!xmppAddr.equals(other.xmppAddr))
			return false;
		if (xmppDomain == null) {
			if (other.xmppDomain != null)
				return false;
		} else if (!xmppDomain.equals(other.xmppDomain))
			return false;
		if (xmppPort == null) {
			if (other.xmppPort != null)
				return false;
		} else if (!xmppPort.equals(other.xmppPort))
			return false;
		if (xmppPortSecure == null) {
			if (other.xmppPortSecure != null)
				return false;
		} else if (!xmppPortSecure.equals(other.xmppPortSecure))
			return false;
		if (xmppSecurePort == null) {
			if (other.xmppSecurePort != null)
				return false;
		} else if (!xmppSecurePort.equals(other.xmppSecurePort))
			return false;
		return true;
	}
}
