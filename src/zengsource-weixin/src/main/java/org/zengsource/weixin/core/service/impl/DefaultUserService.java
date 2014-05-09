/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import org.apache.commons.lang.StringUtils;
import org.zengsource.weixin.core.dao.UserDao;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.service.UserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultUserService implements UserService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private UserDao userDao;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefaultUserService() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public User findByOpenId(String openId) {
		if (StringUtils.isNotEmpty(openId) && getUserDao() != null) {
			return getUserDao().queryByOpenId(openId);
		}
		return null;
	}

	@Override
	public User create(User user) {
		if (getUserDao() != null) {
			user = getUserDao().insert(user);
		}
		return user;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
