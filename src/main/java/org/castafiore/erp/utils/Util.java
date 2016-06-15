package org.castafiore.erp.utils;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Organization;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.ex.EXWebServletAwareApplication;

public class Util {
	
	public static String getRemoteUser(){
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null && app instanceof EXWebServletAwareApplication){
			Object credential = ((EXWebServletAwareApplication)app).getRequest().getSession().getAttribute("credential");
			if(credential != null){
				//newBaseErpModel.setCreatedBy(credential.toString());
				return credential.toString();
			}
					
		}
		return "anonymous";
	}
	
	
	public static Organization getOrganization(){
		return (Organization)getDAO().getReadOnlySession().load(Organization.class, 1);	
	}
	
	
	public static Dao getDAO(){
		return SpringUtil.getBeanOfType(Dao.class);
	}
	
	public static <T extends BaseErpModel> T get(Integer id,Class<T> type){
		return (T)getDAO().getSession().get(type, id);
	}
	
	public static void reload(BaseErpModel model){
		getDAO().getSession().load(model, model.getId());
	}

}
