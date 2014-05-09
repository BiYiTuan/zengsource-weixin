/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.service.NodeService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultNodeService implements NodeService {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Node getNodeByName(String name) {
		return getNodeByClass(name);
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
