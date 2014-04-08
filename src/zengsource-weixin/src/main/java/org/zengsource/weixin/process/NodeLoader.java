/**
 * 
 */
package org.zengsource.weixin.process;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface NodeLoader {

	public Node getNodeByName(String name);

	public Node getNodeByClass(String className);

}
