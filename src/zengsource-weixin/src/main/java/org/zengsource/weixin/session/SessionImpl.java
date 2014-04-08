/**
 * 
 */
package org.zengsource.weixin.session;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.Execution;
import org.zengsource.weixin.process.ProcessManager;
import org.zengsource.weixin.process.node.ResponseMessages;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class SessionImpl implements Session, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

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
		}
		// else { // 继续执行
		// execution = execution.next();
		// }
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
