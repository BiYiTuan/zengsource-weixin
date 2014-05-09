/**
 * 
 */
package org.zengsource.weixin.core.dao;

import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface UserDao {

	public User queryByOpenId(String openId);

	public User insert(User user);

}
