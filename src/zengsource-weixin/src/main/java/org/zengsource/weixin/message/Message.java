/**
 * 
 */
package org.zengsource.weixin.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class Message implements Parameters, Serializable {

	private static final long serialVersionUID = 1L;

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public static Message fromXml(String xmlIn) {
		try {
			Document doc = DocumentHelper.parseText(xmlIn);
			Element root = doc.getRootElement();
			Message message = new Message();
			message.setFromUserName(root.elementTextTrim(FROM_USER_NAME));
			message.setToUserName(root.elementTextTrim(TO_USER_NAME));
			message.setMsgType(root.elementTextTrim(MSG_TYPE));
			message.setCreateTime(NumberUtils.toLong(root.elementTextTrim(CREATE_TIME), 0));
			if (MSG_TYPE_EVENT.equals(message.getMsgType())) { // 事件
				message.setEvent(root.elementTextTrim(EVENT));
				message.setEventKey(root.elementTextTrim(EVENT_KEY));
				if (EVENT_SUBSCRIBE.equals(message.getEvent())) {
					message.setTicket(root.elementTextTrim(TICKET));
				} else if (EVENT_SCAN.equals(message.getEvent())) {
					message.setTicket(root.elementTextTrim(TICKET));
				} else if (EVENT_LOCATION.equals(message.getEvent())) {
					message.setLatitude(NumberUtils.toFloat(root.elementTextTrim(LATITUDE), 0));
					message.setLongitude(NumberUtils.toFloat(root.elementTextTrim(LONGTITUDE), 0));
					message.setPrecision(NumberUtils.toFloat(root.elementTextTrim(PRECISION), 0));
				}
			} else { // 普通消息
				message.setMsgId(NumberUtils.toLong(root.elementTextTrim(MSG_ID), 0));
				if (MSG_TYPE_TEXT.equals(message.getMsgType())) {
					message.setContent(root.elementTextTrim(CONTENT));
				} else if (MSG_TYPE_IMAGE.equals(message.getMsgType())) {
					message.setPictureUrl(root.elementTextTrim(PIC_URL));
					message.setMediaId(root.elementTextTrim(MEDIA_ID));
				} else if (MSG_TYPE_VOICE.equals(message.getMsgType())) {
					message.setMediaId(root.elementTextTrim(MEDIA_ID));
					message.setFormat(root.elementTextTrim(FORMAT));
				} else if (MSG_TYPE_VIDEO.equals(message.getMsgType())) {
					message.setMediaId(root.elementTextTrim(MEDIA_ID));
					message.setThumbMediaId(root.elementTextTrim(THUMB_MEDIA_ID));
				} else if (MSG_TYPE_LOCATION.equals(message.getMsgType())) {
					message.setLocationX(NumberUtils.toFloat(root.elementTextTrim(LOCALTION_X), 0));
					message.setLocationY(NumberUtils.toFloat(root.elementTextTrim(LOCALTION_Y), 0));
					message.setScale(NumberUtils.toInt(root.elementTextTrim(SCALE), 0));
					message.setLabel(root.elementTextTrim(LABEL));
				} else if (MSG_TYPE_LINK.equals(message.getMsgType())) {
					message.setTitle(root.elementTextTrim(TITLE));
					message.setDescription(root.elementTextTrim(DESCRIPTION));
					message.setUrl(root.elementTextTrim(URL));
				}
			}
			return message;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private String toUserName;
	private String fromUserName;
	private Long createTime;
	private String msgType;
	private String content;
	private Long msgId;
	private String event;
	private String eventKey;
	// 图片
	private String mediaId;
	private String pictureUrl;
	// 语音
	private String format;
	// 视频
	private String thumbMediaId;
	// 位置
	private Float locationX;
	private Float locationY;
	private Integer scale;
	private String label;
	// 链接
	private String title;
	private String description;
	private String url;
	// 扫描二维码事件
	private String ticket;
	// 上报地理位置
	private Float latitude;
	private Float longitude;
	private Float precision;
	// 回复音乐消息
	private String musicUrl;
	private String hqMusicUrl;
	// 回复图文消息
	private List<Article> articles;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Message() {
	}

	public void append(String text) {
		setContent(getContent() + "\n" + text);
	}

	public boolean isDuplicated(Message message) {
		if (getContent().equals(message.getContent())) {
			if (message.getCreateTime() - getCreateTime() < 10000) {
				return true;
			}
		}
		return false;
	}

	public Message replyText() {
		Message out = new Message();
		out.setFromUserName(toUserName);
		out.setToUserName(fromUserName);
		out.setMsgType(MSG_TYPE_TEXT);
		return out;
	}

	public Message replyNews() {
		Message out = new Message();
		out.setFromUserName(toUserName);
		out.setToUserName(fromUserName);
		out.setMsgType(MSG_TYPE_NEWS);
		return out;
	}

	public String toXml() {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("xml");
		root.addElement(FROM_USER_NAME).addCDATA(fromUserName);
		root.addElement(TO_USER_NAME).addCDATA(toUserName);
		root.addElement(CREATE_TIME).addText(createTime.toString());
		root.addElement(MSG_TYPE).addCDATA(msgType);
		if (MSG_TYPE_TEXT.equals(msgType)) {
			root.addElement(CONTENT).addCDATA(content);
		} else if (MSG_TYPE_IMAGE.equals(msgType)) {
			Element imageEl = root.addElement("Image");
			imageEl.addElement(MEDIA_ID).addCDATA(mediaId);
		} else if (MSG_TYPE_VOICE.equals(msgType)) {
			Element voiceEl = root.addElement("Voice");
			voiceEl.addElement(MEDIA_ID).addCDATA(mediaId);
		} else if (MSG_TYPE_VIDEO.equals(msgType)) {
			Element videoEl = root.addElement("Video");
			videoEl.addElement(MEDIA_ID).addCDATA(mediaId);
			videoEl.addElement(TITLE).addCDATA(title);
			videoEl.addElement(DESCRIPTION).addCDATA(description);
		} else if (MSG_TYPE_MUSIC.equals(msgType)) {
			Element musicEl = root.addElement("Music");
			musicEl.addElement(TITLE).addCDATA(title);
			musicEl.addElement(DESCRIPTION).addCDATA(description);
			musicEl.addElement(MUSIC_URL).addCDATA(musicUrl);
			musicEl.addElement(HQ_MUSIC_URL).addCDATA(hqMusicUrl);
			musicEl.addElement(THUMB_MEDIA_ID).addCDATA(thumbMediaId);
		} else if (MSG_TYPE_NEWS.equals(msgType)) {
			if (articles != null) {
				root.addElement("ArticleCount").addText(articles.size() + "");
				Element articlesEle = root.addElement("Articles");
				for (Article article : articles) {
					Element itemEl = articlesEle.addElement("item");
					itemEl.addElement(TITLE).addCDATA(article.getTitle());
					itemEl.addElement(DESCRIPTION).addCDATA(article.getDescription());
					itemEl.addElement(PIC_URL).addCDATA(article.getPicUrl());
					itemEl.addElement(URL).addCDATA(article.getUrl());
				}
			}
		}
		return doc.asXML();
	}

	public String toJson() {
		return "JSON";
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(fromUserName).append(" : ");
		sb.append(msgType).append(" : ");
		if (MSG_TYPE_TEXT.equals(msgType)) {
			sb.append(content);
		} else if (MSG_TYPE_EVENT.equals(msgType)) {
			sb.append(event);
		} else if (MSG_TYPE_IMAGE.equals(msgType)) {
			sb.append(mediaId);
		} else if (MSG_TYPE_VOICE.equals(msgType)) {
			sb.append(mediaId);
		} else if (MSG_TYPE_VIDEO.equals(msgType)) {
			sb.append(mediaId);
		} else if (MSG_TYPE_LOCATION.equals(msgType)) {
			sb.append("(" + locationX + "," + locationY + ")");
		} else if (MSG_TYPE_LINK.equals(msgType)) {
			sb.append(url);
		} else if (MSG_TYPE_MUSIC.equals(msgType)) {
			sb.append(title);
		} else if (MSG_TYPE_NEWS.equals(msgType)) {
			sb.append("Articles - " + articles.size());
		}

		return sb.toString();
	}

	public void addArticle(Article article) {
		if (articles == null) {
			articles = new ArrayList<>();
		}
		articles.add(article);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Float getLocationX() {
		return locationX;
	}

	public void setLocationX(Float locationX) {
		this.locationX = locationX;
	}

	public Float getLocationY() {
		return locationY;
	}

	public void setLocationY(Float locationY) {
		this.locationY = locationY;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
