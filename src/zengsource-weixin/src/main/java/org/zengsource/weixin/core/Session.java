/**
 * 
 */
package org.zengsource.weixin.core;

import java.io.Serializable;

import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Session extends Serializable{

	public Message execute();
	
	public User getUser();

}
