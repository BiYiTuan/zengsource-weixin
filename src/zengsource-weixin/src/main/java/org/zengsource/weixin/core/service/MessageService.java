package org.zengsource.weixin.core.service;

import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface MessageService {

	public Message save(Message message);

	public User requestUserInfo(Message message);

	public Message findByMsgId(Long msgId);
}
