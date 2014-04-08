/**
 * 
 */
package org.zengsource.weixin.process.dao.memory;

import java.util.HashMap;
import java.util.Map;

import org.zengsource.weixin.process.Execution;
import org.zengsource.weixin.process.dao.ExecutionDao;
import org.zengsource.weixin.process.impl.ExecutionImpl;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class MemoryExecutionDao implements ExecutionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static Map<String, Execution> memoryExecutions;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public MemoryExecutionDao() {
		memoryExecutions = new HashMap<String, Execution>();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution query(User user) {
		return memoryExecutions.get(user);
	}

	@Override
	public Execution insert(Execution execution) {
		memoryExecutions.put(execution.getUser().getOpenId(), execution);
		return execution;
	}

	@Override
	public Execution update(Execution execution) {
		ExecutionImpl execution2 = //
		(ExecutionImpl) memoryExecutions.get(execution.getUser().getOpenId());
		execution2.setCurrent(((ExecutionImpl) execution).getCurrent());
		return execution2;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
