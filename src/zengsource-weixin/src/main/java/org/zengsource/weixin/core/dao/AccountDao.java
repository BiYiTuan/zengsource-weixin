/**
 * 
 */
package org.zengsource.weixin.core.dao;

import org.zengsource.weixin.core.domain.Account;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface AccountDao {

	public Account queryByOpenId(String openId);

}
