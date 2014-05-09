/**
 * 
 */
package org.zengsource.weixin.core.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class Node implements Serializable, Parameters {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private int index;
	private String name;
	private String clazz;
	private String msgType;
	private String msgContent;
	private String event;
	private String eventKey;
	private String prompt;
	private String description;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getName() {
		return this.name == null ? getClass().getName() : this.name;
	}

	public boolean matches(Message message) {
		if (message != null && getMsgType().equals(message.getMsgType())) {
			if (MSG_TYPE_TEXT.equals(getMsgType())) {
				if (msgContent != null && message.getContent() != null) {
					return Pattern.matches(msgContent, message.getContent());
				}
			} else if (MSG_TYPE_EVENT.equals(getMsgType())) {
				if (EVENT_CLICK.equals(getEvent())) {
					return Pattern.matches(event, message.getEvent()) // 单击
							&& Pattern.matches(eventKey, message.getEventKey());
				} else if (EVENT_SUBSCRIBE.equals(getEvent())) {
					if (Pattern.matches(event, message.getEvent())) {
						if (eventKey == null && message.getEventKey() == null) {
							return true; // 订阅用户
						} else if (eventKey != null && message.getEventKey() != null) {
							// 扫描二维码
							return Pattern.matches(eventKey, message.getEventKey());
						}
					}
				} else {
					return Pattern.matches(event, message.getEvent());
				}
			}
		}
		return false;
	}
	
	public Message run(Message message) {
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
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
	
	public String getPrompt() {
		return prompt;
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
