/**
 * 
 */
package org.zengsource.weixin.process.impl;

import java.util.ArrayList;
import java.util.List;

import org.zengsource.weixin.process.Definition;
import org.zengsource.weixin.process.Node;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class DefinitionImpl implements Definition {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private int index;
	private String name;
	private String description;
	private List<Node> nodes;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public DefinitionImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Definition addNode(Node node) {
		if (this.nodes == null) {
			this.nodes = new ArrayList<Node>();
		}
		if (node != null) {
			this.nodes.add(node);
		}
		return this;
	}

	@Override
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

	@Override
	public Node getNodeAt(int index) {
		if (this.nodes != null && index >= 0 && index < this.nodes.size()) {
			return this.nodes.get(index);
		}
		return null;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
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
