/**
 * 
 */
package org.zengsource.weixin.process.impl;

import java.util.ArrayList;
import java.util.List;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.DefinitionLoader;
import org.zengsource.weixin.process.Execution;
import org.zengsource.weixin.process.Node;
import org.zengsource.weixin.process.ProcessManager;
import org.zengsource.weixin.process.dao.DefinitionDao;
import org.zengsource.weixin.process.dao.ExecutionDao;
import org.zengsource.weixin.user.User;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ProcessManagerImpl implements ProcessManager {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private String[] defintionFiles;
	private List<Definition> definitions;

	private ExecutionDao executionDao;
	private DefinitionDao definitionDao;
	private DefinitionLoader definitionLoader;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public ProcessManagerImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution findExecution(User user) {
		return this.executionDao.query(user);
	}

	@Override
	public Definition findDefinition(Message message) {
		if (definitions == null || definitions.size() == 0) { // 先从数据源查询
			definitions = this.definitionDao.queryAll();
		}
		if (definitions == null || definitions.size() == 0) { // 从文件加载
			if (defintionFiles == null) {
				definitions = this.loadDefinition(null);
			} else {
				definitions = new ArrayList<Definition>();
				for (String path : defintionFiles) {
					definitions.addAll(this.loadDefinition(path));
				}
			}
		}

		if (definitions == null || definitions.size() == 0) {
			return null;
		}
		for (Definition definition : definitions) {
			Node first = definition.getNodeAt(0);
			if (first != null && first.matches(message)) {
				return definition;
			}
		}
		return null;
	}

	@Override
	public List<Definition> loadDefinition(String xmlPath) {
		List<Definition> definitions = this.definitionLoader.load(xmlPath);
		for (Definition defNew : definitions) {
			Definition defOld = this.definitionDao.queryByName(defNew.getName());
			if (defOld == null) {
				this.definitionDao.insert(defNew);
			}
		}
		return definitions;
	}

	@Override
	public Execution startExecution(User user, Definition definition) {
		Execution execution = new ExecutionImpl(user, definition);
		execution = this.executionDao.insert(execution);
		return execution;
	}

	@Override
	public Execution updateExecution(Execution execution) {
		return this.executionDao.update(execution);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefinitionLoader getDefinitionLoader() {
		return definitionLoader;
	}

	public void setDefinitionLoader(DefinitionLoader definitionLoader) {
		this.definitionLoader = definitionLoader;
	}

	public DefinitionDao getDefinitionDao() {
		return definitionDao;
	}

	public void setDefinitionDao(DefinitionDao definitionDao) {
		this.definitionDao = definitionDao;
	}

	public ExecutionDao getExecutionDao() {
		return executionDao;
	}

	public void setExecutionDao(ExecutionDao executionDao) {
		this.executionDao = executionDao;
	}

	public String[] getDefintionFiles() {
		return defintionFiles;
	}

	public void setDefintionFiles(String[] defintionFiles) {
		this.defintionFiles = defintionFiles;
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
