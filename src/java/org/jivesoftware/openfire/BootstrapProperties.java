package org.jivesoftware.openfire;

public class BootstrapProperties {
	private String mysqlHost;
	private String mysqlPort;
	private String mysqlDb;
	private String mysqlUser;
	private String mysqlPassword;
	private String xmppDomain;
	private String xmppPort;
	private String xmppAddress;
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

	public String getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqlDb() {
		return mysqlDb;
	}

	public String getMysqlUser() {
		return mysqlUser;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public String getXmppDomain() {
		return xmppDomain;
	}

	public String getXmppPort() {
		return xmppPort;
	}

	public String getXmppAddress() {
		return xmppAddress;
	}

	public String getXmppPortSecure() {
		return xmppPortSecure;
	}

	public String getXmppSecurePort() {
		return xmppSecurePort;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public String getEncryptionPort() {
		return encryptionPort;
	}

	public String getHttpPort() {
		return httpPort;
	}

	public String getHttpPortSecure() {
		return httpPortSecure;
	}

	public String getLocale() {
		return locale;
	}

	public static class Builder {
		private String mysqlHost;
		private String mysqlPort;
		private String mysqlDb;
		private String mysqlUser;
		private String mysqlPassword;
		private String xmppDomain;
		private String xmppPort;
		private String xmppAddress;
		private String xmppPortSecure;
		private String xmppSecurePort;
		private String encryptionKey;
		private String encryptionPort;
		private String httpPort;
		private String httpPortSecure;
		private String locale;

		public Builder mysqlHost(String mysqlHost) {
			this.mysqlHost = mysqlHost;
			return this;
		}

		public Builder mysqlPort(String mysqlPort) {
			this.mysqlPort = mysqlPort;
			return this;
		}

		public Builder mysqlDb(String mysqlDb) {
			this.mysqlDb = mysqlDb;
			return this;
		}

		public Builder mysqlUser(String mysqlUser) {
			this.mysqlUser = mysqlUser;
			return this;
		}

		public Builder mysqlPassword(String mysqlPassword) {
			this.mysqlPassword = mysqlPassword;
			return this;
		}

		public Builder xmppDomain(String xmppDomain) {
			this.xmppDomain = xmppDomain;
			return this;
		}

		public Builder xmppPort(String xmppPort) {
			this.xmppPort = xmppPort;
			return this;
		}

		public Builder xmppAddress(String xmppAddress) {
			this.xmppAddress = xmppAddress;
			return this;
		}

		public Builder xmppPortSecure(String xmppPortSecure) {
			this.xmppPortSecure = xmppPortSecure;
			return this;
		}

		public Builder xmppSecurePort(String xmppSecurePort) {
			this.xmppSecurePort = xmppSecurePort;
			return this;
		}

		public Builder encryptionKey(String encryptionKey) {
			this.encryptionKey = encryptionKey;
			return this;
		}

		public Builder encryptionPort(String encryptionPort) {
			this.encryptionPort = encryptionPort;
			return this;
		}

		public Builder httpPort(String httpPort) {
			this.httpPort = httpPort;
			return this;
		}

		public Builder httpPortSecure(String httpPortSecure) {
			this.httpPortSecure = httpPortSecure;
			return this;
		}

		public Builder locale(String locale) {
			this.locale = locale;
			return this;
		}

		public BootstrapProperties build() {
			return new BootstrapProperties(this);
		}
	}

	private BootstrapProperties(Builder builder) {
		this.mysqlHost = builder.mysqlHost;
		this.mysqlPort = builder.mysqlPort;
		this.mysqlDb = builder.mysqlDb;
		this.mysqlUser = builder.mysqlUser;
		this.mysqlPassword = builder.mysqlPassword;
		this.xmppDomain = builder.xmppDomain;
		this.xmppPort = builder.xmppPort;
		this.xmppAddress = builder.xmppAddress;
		this.xmppPortSecure = builder.xmppPortSecure;
		this.xmppSecurePort = builder.xmppSecurePort;
		this.encryptionKey = builder.encryptionKey;
		this.encryptionPort = builder.encryptionPort;
		this.httpPort = builder.httpPort;
		this.httpPortSecure = builder.httpPortSecure;
		this.locale = builder.locale;
	}

	@Override
	public String toString() {
		return "BootstrapProperties [mysqlHost=" + mysqlHost + ", mysqlPort=" + mysqlPort + ", mysqlDb=" + mysqlDb + ", mysqlUser=" + mysqlUser
				+ ", mysqlPassword=" + mysqlPassword + ", xmppDomain=" + xmppDomain + ", xmppPort=" + xmppPort + ", xmppAddress=" + xmppAddress
				+ ", xmppPortSecure=" + xmppPortSecure + ", xmppSecurePort=" + xmppSecurePort + ", encryptionKey=" + encryptionKey + ", encryptionPort="
				+ encryptionPort + ", httpPort=" + httpPort + ", httpPortSecure=" + httpPortSecure + ", locale=" + locale + "]";
	}
	
}
