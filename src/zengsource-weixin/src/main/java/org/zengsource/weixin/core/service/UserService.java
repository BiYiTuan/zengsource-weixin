package org.zengsource.weixin.core.service;

import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface UserService {

	public User create(User user);

	public User findByOpenId(String openId);

}
