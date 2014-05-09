/**
 * 
 */
package org.zengsource.weixin.core.domain;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class Account {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public static final int CERTIFICATED = 1;
	public static final int UNCERTITICATED = 0;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private String appId;
	private String appSecret;
	private String openId;
	private int type;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public boolean isCertificated() {
		return type == CERTIFICATED;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
