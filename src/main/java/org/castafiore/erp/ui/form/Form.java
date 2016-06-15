package org.castafiore.erp.ui.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.layout.EXMigLayout;
import org.castafiore.utils.StringUtil;

public class Form extends EXMigLayout {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String label;
	
	private List<org.castafiore.erp.ui.form.IGroup> groups = new ArrayList<org.castafiore.erp.ui.form.IGroup>(3);
	
	
	public Form(org.castafiore.erp.annotations.Form form, Class<? extends BaseErpModel> vo_, GridController controller, int pageNumber) {
		super(form.name(), form.layout());
		this.label = form.label();
		
		if(StringUtil.isNotEmpty(form.style())){
			setAttribute("style", form.style());
		}
		addClass("page-" + pageNumber);
		for(Group g : form.groups()){
			Class<? extends IGroup> clazz = g.impl();
			IGroup group = instantiateGroup(clazz, g, vo_, controller);
			addChild(group, g.layoutData());
			if(StringUtil.isNotEmpty(g.style())){
				group.setAttribute("style", g.style());
			}
			groups.add(group);
		}
	}
	
	
	public void setEnabled(boolean b){
		for(IGroup g : groups){
			g.setEnabled(b);
		}
	}
	
	private IGroup instantiateGroup(Class<? extends IGroup> clazz, Group group,Class<? extends BaseErpModel> vo, GridController controller){
		try{
		IGroup uiGroup = clazz.getConstructor(Group.class,Class.class,GridController.class).newInstance(group,vo,controller);
		return uiGroup;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public List<org.castafiore.erp.ui.form.IGroup> getGroups(){
		return groups;
	}
	public String getLabel(){
		return label;
	}
	
	public Map<InputControl<?>, List<String>> getValidators(){
		Map<InputControl<?>, List<String>> result = new LinkedHashMap<InputControl<?>, List<String>>();
		for(IGroup g : groups){
			result.putAll(g.getValidators());
		}
		return result;
	}
	
	
	public void fillModel(final BaseErpModel model)	{
		for(org.castafiore.erp.ui.form.IGroup g : groups){
			g.fillModel(model);
		}
	}
	
	public void setData(final BaseErpModel model){
		for(org.castafiore.erp.ui.form.IGroup g : groups){
			g.setData(model);
		}
	}

	
}
