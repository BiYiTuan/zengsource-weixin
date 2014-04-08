/**
 * 
 */
package org.zengsource.weixin.message;

/**
 * 微信提供的所有消息参数名。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Parameters {
	// 通用参数
	public static final String TO_USER_NAME = "ToUserName";
	public static final String FROM_USER_NAME = "FromUserName";
	public static final String CREATE_TIME = "CreateTime";
	// 消息类型
	public static final String MSG_TYPE = "MsgType";
	// 接收普通消息
	public static final String MSG_ID = "MsgId";
	// => 文本消息
	public static final String MSG_TYPE_TEXT = "text";
	public static final String CONTENT = "Content";
	// => 图片消息
	public static final String MSG_TYPE_IMAGE = "image";
	public static final String PIC_URL = "PicUrl";
	public static final String MEDIA_ID = "MediaId";
	// => 语音消息
	public static final String MSG_TYPE_VOICE = "voice";
	public static final String FORMAT = "Format";
	// => 视频消息
	public static final String MSG_TYPE_VIDEO = "video";
	public static final String THUMB_MEDIA_ID = "ThumbMediaId";
	// => 位置消息
	public static final String MSG_TYPE_LOCATION = "location";
	public static final String LOCALTION_X = "Location_X";
	public static final String LOCALTION_Y = "Location_Y";
	public static final String SCALE = "Scale";
	public static final String LABEL = "Label";
	// => 链接消息
	public static final String MSG_TYPE_LINK = "link";
	public static final String TITLE = "Title";
	public static final String DESCRIPTION = "Description";
	public static final String URL = "Url";
	// => 接收事件推送
	public static final String MSG_TYPE_EVENT = "event";
	public static final String EVENT = "Event";
	// ==> 关注/取消关注事件
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	// ==> 扫描带参数二维码事件
	public static final String EVENT_SCAN = "SCAN";
	public static final String EVENT_KEY = "EventKey";
	public static final String EVENT_KEY_QRSCENE = "qrscene_";
	public static final String TICKET = "Ticket";
	// ==> 上报地理位置事件
	public static final String EVENT_LOCATION = "LOCATION";
	public static final String LATITUDE = "Latitude";
	public static final String LONGTITUDE = "Longitude";
	public static final String PRECISION = "Precision";
	// ==> 点击菜单拉取消息
	public static final String EVENT_CLICK = "CLICK";
	// ==> 点击菜单访问链接 VIEW
	public static final String EVENT_VIEW = "VIEW";
	// 被动响应消息
	// => 音乐消息
	public static final String MSG_TYPE_MUSIC = "music";
	public static final String MUSIC_URL = "MusicUrl";
	public static final String HQ_MUSIC_URL = "HQMusicUrl";
	// => 图文消息
	public static final String MSG_TYPE_NEWS = "news";
	public static final String ARTICLE_COUNT = "ArticleCount";
	public static final String ARTICLES = "Articles";
	public static final String ITEM = "item";
	
	// 发送客户消息
	public static final String URL_SEND_MSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	public static final String JSON_SEND_TEXT_MSG = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
	// 被动消息
	public static final String MSG_TYPE_NEWS_TEMPLATE = "<item>" //
			+ "<Title><![CDATA[%s]]></Title>" //
			+ "<Description><![CDATA[%s]]></Description>" //
			+ "<PicUrl><![CDATA[%s]]></PicUrl>" //
			+ "<Url><![CDATA[%s]]></Url>" //
			+ "</item>";
}
