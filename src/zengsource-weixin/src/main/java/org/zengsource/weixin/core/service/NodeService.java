/**
 * 
 */
package org.zengsource.weixin.core.service;

import org.zengsource.weixin.core.domain.Node;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface NodeService {

	/**
	 * 默认为自动根据类名创建实例。实际使用中可以重载些方法，实现自定义的节点映射和查找。
	 * 
	 * @param name
	 * @return
	 */
	public Node getNodeByName(String name);

	/**
	 * 默认为自动根据类名创建实例。实际使用中可以重载些方法，实现自定义的节点映射和查找。
	 * 
	 * @param className
	 * @return
	 */
	public Node getNodeByClass(String className);

}
