/**
 * 
 */
package org.zengsource.weixin.process;

import java.util.List;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface DefinitionLoader {
	
	public static final String BASE_CFG = "/org/zengsource/weixin/process/Base.pd.xml";

	public List<Definition> load(String path);

}
