package org.castafiore.portal.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Page {
	
	@GraphId
	private Long id;

	private String name;

	private String label;

	private String description;

	private Date dateCreated = new Date();

	private Date dateModified = new Date();

	private NavigationNode navigation;

	private List<Configuration> configurations = new LinkedList<Configuration>();
	
	private List<Portlet> portlets = new LinkedList<Portlet>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public NavigationNode getNavigation() {
		return navigation;
	}

	public void setNavigation(NavigationNode navigation) {
		this.navigation = navigation;
	}

	public List<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Configuration> configurations) {
		this.configurations = configurations;
	}

	public List<Portlet> getPortlets() {
		return portlets;
	}

	public void setPortlets(List<Portlet> portlets) {
		this.portlets = portlets;
	}
	
	
}
