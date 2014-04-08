/**
 * 
 */
package org.zengsource.weixin.process;

import java.io.Serializable;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Execution extends Serializable{

	public Message run(Message in);

	/** 转到下一个节点。*/
	public Execution next();

	/** 返回下一个节点。*/
	public Node getNextNode();

	/** 后面是否还有未执行节点。*/
	public boolean hasNext();

	public User getUser();
	
	public Definition getDefinition();

	public int getCurrent();

}
