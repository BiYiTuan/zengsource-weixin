/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.zengsource.weixin.core.dao.ExecutionDao;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.service.ExecutionService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultExecutionService implements ExecutionService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static Map<String, Execution> executionMap;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private ExecutionDao executionDao;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefaultExecutionService() {
		executionMap = new HashMap<>();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution find(User user) {
		if (getExecutionDao() != null) {
			return getExecutionDao().queryByUser(user);
		}
		// 缓存在内存中
		return executionMap.get(user);
	}

	@Override
	public Execution create(Execution execution) {
		if (getExecutionDao() != null) {
			return getExecutionDao().insert(execution);
		}
		// 缓存在内存中
		executionMap.put(execution.getUser().getOpenId(), execution);
		return execution;
	}

	@Override
	public Execution update(Execution execution) {
		if (getExecutionDao() != null) {
			return getExecutionDao().update(execution);
		}
		// 修改缓存
		Execution execution2 = (Execution) executionMap.get(execution.getUser().getOpenId());
		if (execution2 != null) {
			execution2.setCurrent(((Execution) execution).getCurrent());
		}
		return execution2;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public ExecutionDao getExecutionDao() {
		return executionDao;
	}

	public void setExecutionDao(ExecutionDao executionDao) {
		this.executionDao = executionDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
