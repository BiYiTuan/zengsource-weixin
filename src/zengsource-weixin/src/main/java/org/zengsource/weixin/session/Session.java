/**
 * 
 */
package org.zengsource.weixin.session;

import java.io.Serializable;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Session extends Serializable{

	public Message execute();
	
	public User getUser();

}
