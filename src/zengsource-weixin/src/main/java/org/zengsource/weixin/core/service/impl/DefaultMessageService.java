/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.zengsource.weixin.core.Constants;
import org.zengsource.weixin.core.dao.MessageDao;
import org.zengsource.weixin.core.domain.Account;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.service.AccountService;
import org.zengsource.weixin.core.service.CustomerMessager;
import org.zengsource.weixin.core.service.MessageService;
import org.zengsource.weixin.core.utils.HttpUtils;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultMessageService implements MessageService, Constants {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private MessageDao messageDao;
	private AccountService accountService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefaultMessageService() {
	}

	public DefaultMessageService(String appId, String appSecret) {
		this();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Message findByMsgId(Long msgId) {
		if (msgId != null && getMessageDao() != null) {
			return getMessageDao().queryByMsgId(msgId);
		}
		return null;
	}

	@Override
	public Message save(Message message) {
		if (message != null && getMessageDao() != null) {
			message = getMessageDao().insert(message);
		}
		return message;
	}

	@Override
	public User requestUserInfo(Message message) {
		User user = new User();
		user.setOpenId(message.getFromUserName());
		if (getAccountService() != null) { // 查询帐号信息
			Account account = accountService.findByOpenId(message.getToUserName());
			if (account != null && account.isCertificated()) { // 认证帐号才支持查询用户信息
				CustomerMessager customerMessager = CustomerMessager.getInstance();
				String accessToken = customerMessager.requestAccessToken( //
						account.getAppId(), account.getAppSecret());
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
						if (rootNode.path(ERROR_CODE) != null) { // 没有错误
							user.setNickname(rootNode.path(USER_NICKNAME).getTextValue());
							user.setSex(rootNode.path(USER_SEX).getIntValue());
							user.setHeadImage(rootNode.path(USER_HEAD_IMG_URL).getTextValue());
							// user.setOpenId(rootNode.path(OPEN_ID).getTextValue());
							user.setSubscribe(rootNode.path(USER_SUBSCRIBE).getIntValue());
							user.setSubscribeTime(rootNode.path(USER_SUBSCRIBE_TIME)
									.getTextValue());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return user;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
