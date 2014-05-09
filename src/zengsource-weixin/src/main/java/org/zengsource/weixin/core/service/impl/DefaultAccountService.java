/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import org.zengsource.weixin.core.dao.AccountDao;
import org.zengsource.weixin.core.domain.Account;
import org.zengsource.weixin.core.service.AccountService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultAccountService implements AccountService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	private AccountDao accountDao;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	@Override
	public Account findByOpenId(String openId) {
		if (getAccountDao() != null) {
			return getAccountDao().queryByOpenId(openId);
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
