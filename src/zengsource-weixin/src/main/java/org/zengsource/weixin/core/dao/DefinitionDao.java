/**
 * 
 */
package org.zengsource.weixin.core.dao;

import java.util.List;

import org.zengsource.weixin.core.domain.Definition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface DefinitionDao {

	public List<Definition> queryAll();

	public Definition insert(Definition definition);

	public Definition queryByName(String name);

}
