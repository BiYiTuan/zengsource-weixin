/**
 * 
 */
package org.zengsource.weixin.process.impl;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.Execution;
import org.zengsource.weixin.process.Node;
import org.zengsource.weixin.process.node.ResponseMessages;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ExecutionImpl implements Execution, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private User user;
	private Definition definition;
	private int current;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public ExecutionImpl() {
	}

	public ExecutionImpl(User user, Definition definition) {
		this();
		this.user = user;
		this.definition = definition;
		this.current = 0;
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution next() {
		if (hasNext()) {
			current++; // 移到下一个节点
		} else {
			current = -1;
		}
		return this;
	}

	private void stop() {
		current = -1;
	}

	@Override
	public Node getNextNode() {
		if (hasNext()) {
			return definition.getNodeAt(current + 1);
		}
		return null;
	}

	private Node getCurrentNode() {
		return definition.getNodeAt(current);
	}

	@Override
	public Message run(Message in) {
		Message out = null;
		// 执行当前节点
		Node node = definition.getNodeAt(current);
		if (node.matches(in)) {
			out = node.run(in);
		}
		if (out == null) {
			// 未执行完，但是处理失败；则报错，提示用户重新输入。
			out = in.replyText(); // TODO 显示随机回复
			out.setContent(ERROR_UNKNOWN_OPERATION);
			out.append(getCurrentNode().getPrompt());
		} else if (out != null) {
			if (hasNext()) { // 添加下一节点提示信息
				out.append(getNextNode().getPrompt());
				next(); // 本节点完成，转到下一节点
			} else {
				stop();
			}
		}
		return out;
	}

	@Override
	public boolean hasNext() {
		return current + 1 < definition.getNodes().size();
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public User getUser() {
		return user;
	}

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
