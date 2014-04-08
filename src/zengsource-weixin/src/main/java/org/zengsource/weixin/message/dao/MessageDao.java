/**
 * 
 */
package org.zengsource.weixin.message.dao;

import org.zengsource.weixin.message.Message;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface MessageDao {

	public Message queryLast(Message message);

	public Message insert(Message message);

}
