package org.castafiore.erp.ui.grid;

import java.util.List;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.utils.BaseSpringUtil;

public class GridController {
	
	

	public GridController() {
	}

	
	public BaseErpModel activate(BaseErpModel model){
		if(model.getId() == null){
			return model;
		}else{
			String className = model.getClass().getName();
			if(className.contains("_$$_")){
				className = className.substring(0,className.indexOf("_$$_"));
				model =(BaseErpModel)SpringUtil.getBeanOfType(Dao.class).getSession().load(className,model.getId());
			}else{
				SpringUtil.getBeanOfType(Dao.class).getSession().load(model, model.getId());
			}
			return model;
		}
		
	}
	public void enterButton(int rowNumber, BaseErpModel persistentObject) {
		doubleClick(rowNumber, persistentObject);
	}

	public void doubleClick(int rowNumber, BaseErpModel persistentObject) {
	}

	public void selectedRow( BaseErpModel persistentObject) {
	}

	

	public boolean insertRecord(BaseErpModel newBaseErpModel)
			throws Exception {
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null && app instanceof EXWebServletAwareApplication){
			Object credential = ((EXWebServletAwareApplication)app).getRequest().getSession().getAttribute("credential");
			if(credential != null){
				newBaseErpModel.setCreatedBy(credential.toString());
				newBaseErpModel.setModifiedBy(credential.toString());
			}
					
		}
		
		BaseSpringUtil.getBeanOfType(Dao.class).getSession().save(newBaseErpModel);
		return true;
	}

	public boolean updateRecord(BaseErpModel model) throws Exception {
		Application app = CastafioreApplicationContextHolder.getCurrentApplication();
		if(app != null && app instanceof EXWebServletAwareApplication){
			Object credential = ((EXWebServletAwareApplication)app).getRequest().getSession().getAttribute("credential");
			if(credential != null){
				model.setModifiedBy(credential.toString());
			}
					
		}
		BaseSpringUtil.getBeanOfType(Dao.class).getSession().update(model);
		return true;
	}
	
	public int deleteRecords(Integer[] ids, Class<? extends BaseErpModel> model)
			throws Exception {
			
			String deletes = "delete from " + model.getName() + " where id in(:ids)"; 
			return BaseSpringUtil.getBeanOfType(Dao.class).getSession().createQuery(deletes).setParameterList("ids", ids).executeUpdate();
	}
	
	
	public BaseErpModel getRecordById(Integer id, Class<? extends BaseErpModel> model){
		return (BaseErpModel)BaseSpringUtil.getBeanOfType(Dao.class).getReadOnlySession().get(model, id);
	}

	public boolean deleteRecords(List<BaseErpModel> persistentObjects)
			throws Exception {
		for(BaseErpModel model : persistentObjects){
			BaseSpringUtil.getBeanOfType(Dao.class).getSession().delete(model);
		}
		return true;
	}

	public BaseErpModel createBaseErpModel(Class<? extends BaseErpModel> clazz) throws Exception {
		return clazz.newInstance();
	}

}
