/**
 * 
 */
package org.zengsource.weixin.user.dao.memory;

import java.util.HashMap;
import java.util.Map;

import org.zengsource.weixin.user.User;
import org.zengsource.weixin.user.dao.UserDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class MemoryUserDao implements UserDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static Map<String, User> memoryUsers;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public MemoryUserDao() {
		memoryUsers = new HashMap<String, User>();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public User query(String openId) {
		return memoryUsers.get(openId);
	}

	@Override
	public User insert(User user) {
		memoryUsers.put(user.getOpenId(), user);
		return user;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
