/**
 * 
 */
package org.zengsource.weixin.process.dao;

import org.zengsource.weixin.process.Execution;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ExecutionDao {

	public Execution query(User user);

	public Execution insert(Execution execution);

	public Execution update(Execution execution);

}
