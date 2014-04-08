/**
 * 
 */
package org.zengsource.weixin.message.io;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.zengsource.weixin.Constants;
import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.user.User;
import org.zengsource.weixin.utils.HttpUtils;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class SenderImpl implements Constants, Sender {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private String appId = "wx39acbbc727a861eb";
	private String appSecret = "76f8c0585cbff7d8e146dd514311c61a";

	private String accountId = null;

	private String accessToken;
	private Date expiredTime;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public SenderImpl() {
	}

	public SenderImpl(String appId, String appSecret) {
		this.appId = appId;
		this.appSecret = appSecret;
	}

	@Override
	public User getUser(Message message) {
		if (accountId == null) {
			accountId = message.getToUserName();
		}
		String accessToken = this.requestAccessToken();
		if (accessToken == null) {
			throw new RuntimeException("Cannot get access token!");
		}
		String responseBody = HttpUtils.post(URL_GET_USER, //
				new BasicNameValuePair(ACCESS_TOKEN, accessToken), //
				new BasicNameValuePair(OPEN_ID, message.getFromUserName()), //
				new BasicNameValuePair(LANG, LANG_ZH_CN));
		logger.info("==> Weixin return: \n" + responseBody);
		if (responseBody != null) {
			ObjectMapper om = new ObjectMapper();
			try {
				JsonNode rootNode = om.readTree(responseBody);
				User user = new User();
				user.setNickname(rootNode.path(USER_NICKNAME).getTextValue());
				user.setSex(rootNode.path(USER_SEX).getIntValue());
				user.setHeadImage(rootNode.path(USER_HEAD_IMG_URL).getTextValue());
				user.setOpenId(rootNode.path(OPEN_ID).getTextValue());
				user.setSubscribe(rootNode.path(USER_SUBSCRIBE).getIntValue());
				user.setSubscribeTime(rootNode.path(USER_SUBSCRIBE_TIME).getTextValue());
				return user;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 向微信发起请求前，获取access_token，然后缓存在本地，未过期前重复使用。 */
	private String requestAccessToken() {
		if (StringUtils.isNotBlank(accessToken) && (new Date()).before(expiredTime)) {
			return this.accessToken;
		}
		// 发送请求到微信，返回 {"access_token":"ACCESS_TOKEN","expires_in":7200}
		String responseBody = HttpUtils.get(URL_GET_TOKEN, //
				new BasicNameValuePair(GRANT_TYPE, CLIENT_CREDENTIAL), //
				new BasicNameValuePair(APP_ID, appId), //
				new BasicNameValuePair(SECRET, appSecret));
		// 保存到本地
		if (responseBody != null) {
			ObjectMapper om = new ObjectMapper();
			try {
				JsonNode rootNode = om.readTree(responseBody);
				this.accessToken = rootNode.path(ACCESS_TOKEN).getTextValue();
				this.expiredTime = DateUtils.addSeconds(new Date(), //
						NumberUtils.toInt( //
								rootNode.path(EXPIRE_IN_SEC).getTextValue(), 72));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.accessToken;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
