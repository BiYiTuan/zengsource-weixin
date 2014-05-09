/**
 * 
 */
package org.zengsource.weixin.core.service;

import java.util.List;

import org.zengsource.weixin.core.domain.Definition;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface DefinitionService {

	public List<Definition> findAll();

	public List<Definition> load(String xmlPath);

	public Definition findByName(String name);

	public Definition create(Definition defNew);

}
