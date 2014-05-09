/**
 * 
 */
package org.zengsource.weixin.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class Definition implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private int index;
	private String name;
	private String description;
	private List<Node> nodes;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Definition() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public Definition addNode(Node node) {
		if (this.nodes == null) {
			this.nodes = new ArrayList<Node>();
		}
		if (node != null) {
			this.nodes.add(node);
		}
		return this;
	}

	public Node getNode(String name) {
		if (name != null && this.nodes != null) {
			for (Node node : this.nodes) {
				if (name.equals(node.getName())) {
					return node;
				}
			}
		}
		return null;
	}

	public Node getNodeAt(int index) {
		if (this.nodes != null && index >= 0 && index < this.nodes.size()) {
			return this.nodes.get(index);
		}
		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
