/**
 * 
 */
package org.zengsource.weixin.core.node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.Constants;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.domain.Parameters;

/**
 * 专门用于接收微信事件消息的节点。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class EventNode extends Node implements Constants, Parameters, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public String getMsgType() {
		return MSG_TYPE_EVENT;
	}

	@Override
	public Message run(Message in) {
		String event = in.getEvent();
		logger.info("==> Event message : " + in.getEvent());
		if (EVENT_SUBSCRIBE.equals(event)) { // 订阅
			return this.onSubscribe(in);
		} else if (EVENT_UNSUBSCRIBE.equals(event)) { // 取消订阅
			return this.onUnsubscribe(in);
		} else if (EVENT_CLICK.equals(event)) { // 单击菜单
			return this.onClick(in);
		}
		return null;
	}

	/** 处理用户订阅。 */
	protected Message onSubscribe(Message in) {
		Message out = in.replyText();
		out.setContent(INFO_WELCOME_TO_SUBSTRIBE);
		return out;
	}

	/** 处理用户取消订阅。 */
	protected Message onUnsubscribe(Message in) {
		Message out = in.replyText();
		out.setContent(INFO_FAREWELL_UNSUBSTRIBE);
		return out;
	}

	/** 处理用户点击菜单。 */
	protected Message onClick(Message in) {
		String eventKey = in.getEventKey();
		logger.info("==> Event key: " + eventKey);
		Message out = in.replyText();
		out.setContent("您点击了 " + eventKey);
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
