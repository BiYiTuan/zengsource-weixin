/**
 * 
 */
package org.zengsource.weixin.process.impl;

import org.zengsource.weixin.process.Node;
import org.zengsource.weixin.process.NodeLoader;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ReflectionNodeLoader implements NodeLoader {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Node getNodeByName(String name) {
		return null;
	}

	@Override
	public Node getNodeByClass(String className) {
		Object object = null;
		try {
			object = Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Node) object;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
