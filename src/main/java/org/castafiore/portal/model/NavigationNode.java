package org.castafiore.portal.model;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class NavigationNode {

	@GraphId
	private Long id;

	private String name;

	private String label;

	private String permission;

	private List<NavigationNode> children = new LinkedList<NavigationNode>();
	
	private String iconClass;
	
	private Page page = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<NavigationNode> getChildren() {
		return children;
	}

	public void setChildren(List<NavigationNode> children) {
		this.children = children;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	
}
