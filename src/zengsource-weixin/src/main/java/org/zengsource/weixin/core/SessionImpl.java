/**
 * 
 */
package org.zengsource.weixin.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.node.ResponseMessages;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class SessionImpl implements Session, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private Message message;

	private User user;

	private ProcessManager processManager;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public SessionImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Message execute() {
		Message out = null;
		if (user == null) {
			// TODO
		}
		Execution execution = getProcessManager().findExecution(user);
		if (execution == null) {
			Definition definition = getProcessManager().findDefinition(message);
			if (definition != null) { // 第一次执行
				execution = getProcessManager().startExecution(user, definition);
			}
		} else { // 继续执行
			logger.info("==> Continue execution : " + execution.getDefinition().getName());
		}
		if (execution != null) {
			out = execution.run(message);
			getProcessManager().updateExecution(execution);
		}
		return out;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProcessManager getProcessManager() {
		return processManager;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
