package org.zengsource.weixin.core.service;

import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ExecutionService {

	public Execution find(User user);

	public Execution create(Execution execution);

	public Execution update(Execution execution);

}
