/**
 * 
 */
package org.zengsource.weixin.process;

import java.io.Serializable;

import org.zengsource.weixin.message.Message;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Node extends Serializable {

	public String getName();

	/** 节点的提示信息。*/
	public String getPrompt();

	public boolean matches(Message message);

	public Message run(Message in);

}
