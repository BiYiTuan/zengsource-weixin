/**
 * 
 */
package org.zengsource.weixin.core.service;

import org.zengsource.weixin.core.domain.Account;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface AccountService {

	public Account findByOpenId(String openId);

}
