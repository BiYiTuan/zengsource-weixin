/**
 * 
 */
package org.zengsource.weixin.core.domain;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.node.ResponseMessages;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class Execution implements Serializable, ResponseMessages {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private User user;
	private Definition definition;
	private int current;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Execution() {
	}

	public Execution(User user, Definition definitionInf) {
		this();
		this.user = user;
		this.definition = definitionInf;
		this.current = 0;
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	/** 转到下一个节点。 */
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

	/** 后面是否还有未执行节点。 */
	public boolean hasNext() {
		return current + 1 < definition.getNodes().size();
	}

	/** 返回下一个节点。 */
	public Node getNextNode() {
		if (hasNext()) {
			return definition.getNodeAt(current + 1);
		}
		return null;
	}

	private Node getCurrentNode() {
		return definition.getNodeAt(current);
	}

	public Message run(Message in) {
		Message out = null;
		// 执行当前节点
		Node node = definition.getNodeAt(current);
		if (node.matches(in)) {
			logger.info("==> To run node : " + node.getName());
			out = node.run(in);
		}
		if (out == null) {
			// 未执行完，但是处理失败；则报错，提示用户重新输入。
			out = in.replyText(); // TODO 显示随机回复
			out.setContent(ERROR_UNKNOWN_OPERATION);
			out.append(getCurrentNode().getPrompt());
			logger.info("==> Run node failed!");
		} else if (out != null) {
			if (out.getType() == Message.TYPE_ERROR) {
				// 出错，报错后重复此节点
				out.append(getCurrentNode().getPrompt());
				logger.info("==> Run node with error!");
			} else if (out.getType() == Message.TYPE_ABORT || !hasNext()) {
				stop(); // 中止本次流程
				logger.info("==> Call to abort the process!");
			} else { // 添加下一节点提示信息
				out.append(getNextNode().getPrompt());
				next(); // 本节点完成，转到下一节点
				logger.info("==> Success! Move to next node.");
			}
		}
		return out;
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
