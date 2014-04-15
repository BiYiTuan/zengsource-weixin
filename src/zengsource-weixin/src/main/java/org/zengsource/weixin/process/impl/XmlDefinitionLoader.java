/**
 * 
 */
package org.zengsource.weixin.process.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.zengsource.weixin.message.Parameters;
import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.DefinitionLoader;
import org.zengsource.weixin.process.NodeLoader;
import org.zengsource.weixin.process.node.AbstractNode;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class XmlDefinitionLoader implements DefinitionLoader, Parameters {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private NodeLoader nodeLoader;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public XmlDefinitionLoader() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<Definition> load(String path) {
		List<Definition> definitions = new ArrayList<Definition>();
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = (path == null) ? saxReader.read( //
					ClassLoader.class.getClassLoader().getResourceAsStream(BASE_CFG)) //
					: saxReader.read(new File(path));
			Element root = doc.getRootElement();
			for (Object obj : root.elements()) {
				Element defEle = (Element) obj;
				DefinitionImpl def = new DefinitionImpl();
				def.setName(defEle.elementTextTrim("name"));
				def.setDescription(defEle.elementTextTrim("desc"));
				def.setIndex(NumberUtils.toInt(defEle.elementTextTrim("index"), 0));
				Element nodesEle = defEle.element("nodes");
				for (Object obj2 : nodesEle.elements()) {
					Element nodeEle = (Element) obj2;
					String name = nodeEle.elementTextTrim("name");
					String clazz = nodeEle.elementTextTrim("class");
					AbstractNode node = (AbstractNode) getNodeLoader().getNodeByName(name);
					if (node == null) {
						node = (AbstractNode) getNodeLoader().getNodeByClass(clazz);
					}
					node.setClazz(clazz);
					node.setName(name);
					node.setMsgType(nodeEle.elementTextTrim("msgType"));
					if (MSG_TYPE_TEXT.equals(node.getMsgType())) {
						node.setMsgContent(nodeEle.elementTextTrim("msgContent"));
					} else if (MSG_TYPE_EVENT.equals(node.getMsgType())) {
						node.setEvent(nodeEle.elementTextTrim("event"));
						if (EVENT_CLICK.equals(node.getEvent())) {
							node.setEventKey(nodeEle.elementTextTrim("eventKey"));
						}
					}
					//node.setPrompt(nodeEle.elementTextTrim("prompt"));
					def.addNode(node);
				}
				definitions.add(def);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return definitions;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public NodeLoader getNodeLoader() {
		if (nodeLoader == null) {
			nodeLoader = new ReflectionNodeLoader();
		}
		return nodeLoader;
	}

	public void setNodeLoader(NodeLoader nodeLoader) {
		this.nodeLoader = nodeLoader;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
