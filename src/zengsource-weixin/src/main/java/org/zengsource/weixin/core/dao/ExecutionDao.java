/**
 * 
 */
package org.zengsource.weixin.core.dao;

import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ExecutionDao {

	public Execution queryByUser(User user);

	public Execution insert(Execution execution);

	public Execution update(Execution execution);

}
