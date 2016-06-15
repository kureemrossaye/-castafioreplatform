package org.castafiore.erp.security;

import java.util.List;

import org.castafiore.erp.ActionMatrix;
import org.castafiore.erp.EmployeeGroup;
import org.castafiore.erp.EmployeeRole;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.persistence.Dao;
import org.hibernate.criterion.Restrictions;

public class SecurityService {
	
	private Dao dao;

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	
	
	public List<EmployeeGroup> getGroups(){
		return dao.getReadOnlySession().createCriteria(EmployeeGroup.class).list();
	}
	
	public List<EmployeeRole> getRoles (){
		return dao.getReadOnlySession().createCriteria(EmployeeRole.class).list();
	}
	
	
	
	
	public ActionMatrix getMatrix(Class<? extends AbstractEXModule> module, String action){
		return (ActionMatrix)dao.getReadOnlySession().createCriteria(ActionMatrix.class).add(Restrictions.eq("action", action)).add(Restrictions.eq("module", module.getName())).uniqueResult();
	}
	
	public List<ActionMatrix> getMatrices(Class<? extends AbstractEXModule> module){
		List l  = dao.getReadOnlySession().createCriteria(ActionMatrix.class).add(Restrictions.eq("module", module.getName())).list();
		return l;
	}
	
	
	
	
	public void login(String username, String password){
		
	}

}
