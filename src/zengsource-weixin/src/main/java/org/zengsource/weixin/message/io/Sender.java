/**
 * 
 */
package org.zengsource.weixin.message.io;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Sender {
	
	
	public void setAppId(String appId);
	
	public void setAppSecret(String appSecret);

	public User getUser(Message message);

	public Message send(Message notice);

}
