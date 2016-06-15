package org.castafiore.erp;

import java.util.ArrayList;
import java.util.List;


public class CommentableErpModel extends BaseErpModel{
	
	private List<UserComment> comments = new ArrayList<UserComment>();
	
	public List<UserComment> getComments() {
		return comments;
	}

	public void setComments(List<UserComment> comments) {
		this.comments = comments;
	}

}
