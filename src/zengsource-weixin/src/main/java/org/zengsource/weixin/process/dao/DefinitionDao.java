/**
 * 
 */
package org.zengsource.weixin.process.dao;

import java.util.List;

import org.zengsource.weixin.process.Definition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface DefinitionDao {

	public List<Definition> queryAll();

	public Definition queryByName(String name);

	public Definition insert(Definition definition);

}
