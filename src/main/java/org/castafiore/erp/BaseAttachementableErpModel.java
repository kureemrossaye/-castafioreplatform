package org.castafiore.erp;

import java.util.ArrayList;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.List;


import org.castafiore.wfs.types.File;

@NodeEntity
public class BaseAttachementableErpModel extends CommentableErpModel{
	
	private List<File> attachements = new ArrayList<File>();
	
	public List<File> getAttachements() {
		return attachements;
	}

	public void setAttachements(List<File> attachements) {
		this.attachements = attachements;
	}

}
