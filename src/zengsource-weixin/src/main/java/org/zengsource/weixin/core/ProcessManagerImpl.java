/**
 * 
 */
package org.zengsource.weixin.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.core.domain.Definition;
import org.zengsource.weixin.core.domain.Execution;
import org.zengsource.weixin.core.domain.Message;
import org.zengsource.weixin.core.domain.Node;
import org.zengsource.weixin.core.domain.User;
import org.zengsource.weixin.core.service.DefinitionService;
import org.zengsource.weixin.core.service.ExecutionService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ProcessManagerImpl implements ProcessManager {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private String[] defintionFiles;
	private List<Definition> definitions;

	private ExecutionService executionService;
	private DefinitionService definitionService;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public ProcessManagerImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Execution findExecution(User user) {
		return this.executionService.find(user);
	}

	@Override
	public Definition findDefinition(Message message) {
		if (definitions == null || definitions.size() == 0) { // 先从数据源查询
			definitions = this.definitionService.findAll();
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
		List<Definition> definitions = this.definitionService.load(xmlPath);
		for (Definition defNew : definitions) {
			Definition defOld = this.definitionService.findByName(defNew.getName());
			if (defOld == null) {
				this.definitionService.create(defNew);
			}
		}
		return definitions;
	}

	@Override
	public Execution startExecution(User user, Definition definition) {
		Execution execution = new Execution(user, definition);
		execution = this.executionService.create(execution);
		logger.info("==> Start a new execution : " + execution.getDefinition().getName());
		return execution;
	}

	@Override
	public Execution updateExecution(Execution execution) {
		return this.executionService.update(execution);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefinitionService getDefinitionService() {
		return definitionService;
	}

	public void setDefinitionService(DefinitionService definitionService) {
		this.definitionService = definitionService;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
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
