package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;

public class Status extends BaseErpModel{
	
	private Workflow workflow;
	
	private BigInteger state;

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public BigInteger getState() {
		return state;
	}

	public void setState(BigInteger state) {
		this.state = state;
	}

	

}
