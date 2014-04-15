/**
 * 
 */
package org.zengsource.weixin.message.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

	@Override
	public Message send(final Message message) {
		String accessToken = this.requestAccessToken();
		if (accessToken == null) {
			throw new RuntimeException("Cannot get access token!");
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			URI uri = new URIBuilder(URL_SEND_MSG).setParameter(ACCESS_TOKEN, accessToken).build();
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			String json = message.toJson();
			StringEntity entity = new StringEntity( //
					json, ContentType.create("application/json", "utf-8"));
			// entity.setContentType("application/json; charset=utf-8");
			// entity.setContentEncoding("utf-8");
			httpPost.setEntity(entity);
			logger.info("==> Http Post Url : " + uri.toString());
			logger.info("==> Http Post Json : " + json);
			ResponseHandler<Integer> responseHandler = new ResponseHandler<Integer>() {
				@Override
				public Integer handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					StatusLine statusLine = response.getStatusLine();
					message.setResponseStatus(statusLine.getStatusCode());
					logger.info("==> Http Post Status : " + statusLine.getStatusCode());
					if (statusLine.getStatusCode() >= 300) {
						throw new HttpResponseException(statusLine.getStatusCode(),
								statusLine.getReasonPhrase());
					}
					HttpEntity entity = response.getEntity();
					if (entity == null) {
						throw new ClientProtocolException("Response contains no content.");
					}
					ContentType contentType = ContentType.getOrDefault(entity);
					Charset charset = contentType.getCharset();
					Reader reader = new InputStreamReader(entity.getContent(),
							charset == null ? Charset.forName("utf-8") : charset);
					message.setResponseMime(contentType.getMimeType());
					logger.info("==> Http Post Mime : " + message.getResponseMime());
					if (message.getResponseMime().contains("json")) {
						ObjectMapper mapper = new ObjectMapper();
						JsonNode rootNode = mapper.readTree(reader);
						message.setResponseText(rootNode.asText());
						return rootNode.path("errcode").getIntValue();
					}
					return 0;
				}
			};
			int errCode = httpClient.execute(httpPost, responseHandler);
			if (errCode > 0) {
				message.setErrorCode(errCode);
				logger.error("==> Weixin error : " + errCode);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
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
