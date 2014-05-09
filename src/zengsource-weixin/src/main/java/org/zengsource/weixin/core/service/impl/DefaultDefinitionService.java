/**
 * 
 */
package org.zengsource.weixin.core.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.zengsource.weixin.core.dao.DefinitionDao;
import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.domain.Parameters;
import org.zengsource.weixin.core.service.DefinitionService;
import org.zengsource.weixin.core.service.NodeService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefaultDefinitionService implements DefinitionService, Parameters {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public static final String BASE_CFG = "/org/zengsource/weixin/process/Base.pd.xml";

	private static List<Definition> definitions;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private DefinitionDao definitionDao;

	private NodeService nodeService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefaultDefinitionService() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<Definition> findAll() {
		if (definitions == null) {
			if (getDefinitionDao() != null) {
				definitions = getDefinitionDao().queryAll();
			} else {
				definitions = load(null);
			}
		}
		return definitions;
	}

	@Override
	public Definition create(Definition definition) {
		if (getDefinitionDao() != null) {
			definition = getDefinitionDao().insert(definition);
			definitions = null; // 清除缓存
		}
		return definition;
	}

	@Override
	public Definition findByName(String name) {
		if (definitions == null) {
			this.findAll();
		}
		if (definitions != null) {
			for(Definition definition : definitions) {
				if (definition.getName().equals(name)) {
					return definition;
				}
			}
		}
		return null;
	}

	@Override
	public List<Definition> load(String path) {
		List<Definition> definitionInfs = new ArrayList<Definition>();
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = (path == null) ? saxReader.read( //
					ClassLoader.class.getClassLoader().getResourceAsStream(BASE_CFG)) //
					: saxReader.read(new File(path));
			Element root = doc.getRootElement();
			for (Object obj : root.elements()) {
				Element defEle = (Element) obj;
				Definition def = new Definition();
				def.setName(defEle.elementTextTrim("name"));
				def.setDescription(defEle.elementTextTrim("desc"));
				def.setIndex(NumberUtils.toInt(defEle.elementTextTrim("index"), 0));
				Element nodesEle = defEle.element("nodes");
				for (Object obj2 : nodesEle.elements()) {
					Element nodeEle = (Element) obj2;
					String name = nodeEle.elementTextTrim("name");
					String clazz = nodeEle.elementTextTrim("class");
					Node node = getNodeService().getNodeByName(name);
					if (node == null) {
						node = getNodeService().getNodeByClass(clazz);
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
					node.setPrompt(nodeEle.elementTextTrim("prompt"));
					def.addNode(node);
				}
				definitionInfs.add(def);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return definitionInfs;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public DefinitionDao getDefinitionDao() {
		return definitionDao;
	}

	public void setDefinitionDao(DefinitionDao definitionDao) {
		this.definitionDao = definitionDao;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
