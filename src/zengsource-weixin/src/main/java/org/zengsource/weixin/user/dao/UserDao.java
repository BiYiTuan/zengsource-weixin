/**
 * 
 */
package org.zengsource.weixin.user.dao;

import java.io.Serializable;

import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface UserDao extends Serializable {

	public User query(String openId);

	public User insert(User user);

}
