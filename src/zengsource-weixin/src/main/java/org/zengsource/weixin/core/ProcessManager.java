/**
 * 
 */
package org.zengsource.weixin.core;

import java.util.List;

import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.User;


/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ProcessManager {
	
	/** 从XML定义文件加载流程定义。 */
	public List<Definition> loadDefinition(String xmlPath);

	/** 查找与该消息匹配的流程定义。*/
	public Definition findDefinition(Message message);

	/** 查找该用户目前所在的流程。*/
	public Execution findExecution(User user);

	/** 为用户启动一个流程。*/
	public Execution startExecution(User user, Definition definition);

	/** 更新流程。*/
	public Execution updateExecution(Execution execution);

}
