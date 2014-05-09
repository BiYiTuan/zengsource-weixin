/**
 * 
 */
package org.zengsource.weixin.core.dao;

import org.zengsource.weixin.core.domain.Message;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface MessageDao {

	public Message insert(Message message);

	/** 
	 * 根据消息ID查询已有消息，存在则表示重发消息。
	 * @param msgId
	 * @return
	 */
	public Message queryByMsgId(Long msgId);

}
