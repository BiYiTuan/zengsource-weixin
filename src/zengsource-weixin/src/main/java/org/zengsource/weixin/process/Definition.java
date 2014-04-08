/**
 * 
 */
package org.zengsource.weixin.process;

import java.io.Serializable;
import java.util.List;


/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Definition extends Serializable{
	
	public String getName();

	public int getIndex();
	
	public String getDescription();
	
	public Definition addNode(Node node);

	public Node getNode(String name);
	
	public Node getNodeAt(int index);
	
	public List<Node> getNodes();

}
