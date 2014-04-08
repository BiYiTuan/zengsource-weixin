/**
 * 
 */
package org.zengsource.weixin.process.dao.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.dao.DefinitionDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class MemoryDefinitionDao implements DefinitionDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static Map<String, Definition> memoryDefinitions;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public MemoryDefinitionDao() {
		memoryDefinitions = new HashMap<String, Definition>();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public List<Definition> queryAll() {
		return (List<Definition>) memoryDefinitions.values();
	}
	
	@Override
	public Definition insert(Definition definition) {
		memoryDefinitions.put(definition.getName(), definition);
		return definition;
	}

	@Override
	public Definition queryByName(String name) {
		return memoryDefinitions.get(name);
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
