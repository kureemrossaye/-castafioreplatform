package org.castafiore.erp.ui.form;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.validation.UniqueCodeValidator;
import org.castafiore.erp.ui.form.validation.ValidationUtil;
import org.castafiore.erp.ui.form.validation.Validator;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.sales.StatusActionInterceptor;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class Forms extends EXTabPanel implements TabModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private org.castafiore.erp.annotations.Forms forms_;
	
	private  Class<? extends BaseErpModel> vo_;
	private GridController controller;
	
	
	private BaseErpModel data;
	
	private List<Form> forms = new ArrayList<Form>(3);
	
	private Map<String, String> errorss = new HashMap<String, String>(0);
	
	private List<ActionInterceptor<?>> interceptors = new LinkedList<ActionInterceptor<?>>();

	public Forms(org.castafiore.erp.annotations.Forms forms, Class<? extends BaseErpModel> vo_, GridController controller) {
		super(forms.name(), null);
		this.forms_ = forms;
		this.vo_ = vo_;
		this.controller = controller;
		
		setType(TYPE_PILLS);
		
		
		for(int index = 0; index < size(); index++){
			org.castafiore.erp.annotations.Form form = forms_.forms()[index];
			if(!form.lazy()){
				Form f = new Form(forms_.forms()[index], vo_, controller, index);
				f.setAttribute("findex", index + "");
				this.forms.add(f);
			}else{
				this.forms.add(null);
			}
		}
		
		setModel(this);
		
	}
	
	public void setTabLabel(String label, Integer index){
		tabs.getChildByIndex(index).getChildByIndex(0).setText(label);
	}
	
	public GridController getController(){
		return controller;
	}
	
	public BigInteger getDefaultStatus(){
		for(ActionInterceptor<?> a : interceptors){
			if(a instanceof StatusActionInterceptor){
				return ((StatusActionInterceptor)a).getStatus();
			}
		}
		return null;
	}
	
	public void addActionInterceptor(ActionInterceptor<?> interceptor){
		interceptors.add(interceptor);
	}
	
	protected void fireActionInterceptors(boolean pre, boolean isNew, BaseErpModel entity){
		for(ActionInterceptor<?> a : interceptors){
			if(pre){
				if(isNew){
					a.preSave(entity, this);
				}else{
					a.preUpdate(entity, this);
				}
			}else{
				if(isNew){
					a.postSave(entity, this);
				}else{
					a.postUpdate(entity, this);
				}
			}
			
		}
	}
	
	public void fillModel(){
		
		for(Form f : forms){
			if(f != null)
				f.fillModel(data);
		}

	}
	
	public void setEnabled(boolean b){
		for(Form f : forms){
			if(f != null)
				f.setEnabled(b);
		}
	}
	
	public Map<InputControl<?>, List<String>> getValidators(){
		Map<InputControl<?>, List<String>> result = new LinkedHashMap<InputControl<?>, List<String>>();
		for(Form f : forms){
			result.putAll(f.getValidators());
		}
		return result;
		
	}
	
	
	
	public InputControl<?> getField(String name){
		for(Form f : forms){
			if(f != null){
				for(IGroup g : f.getGroups()){
					InputControl<?> field = g.getField(name);
					if(field != null){
						return field;
					}
				}
			}
		}
		return null;
	}

	
	public void validate()throws ValidationException{
		//Map<InputControl<?>, List<String>> validators = new LinkedHashMap<InputControl<?>, List<String>>();
		List<Error> global = new LinkedList<Error>();
		int index = 0;
		for(Form form : forms){
			
			if(form != null){
				List<Error> errors = new LinkedList<Error>();
				Map<InputControl<?>, List<String>> validators = form.getValidators();
				for(InputControl<?> key : validators.keySet()){
					
					List<String> vals = validators.get(key);
					key.removeClass("qtip-red");
					for(String validator : vals){
						Validator oval = ValidationUtil.getValidator(validator);
						if((oval instanceof UniqueCodeValidator) && data.getId() != null){
							continue;
						}
						Field field =  ReflectionUtils.findField(vo_, key.getName());
						Error error = oval.validate(field, key.getValue());
						if(error != null){
							key.addClass("qtip-red");
							errors.add(error);
						}
					}
				}
				
				
				if(errors.size() == 0){
					tabs.getChildByIndex(index).removeClass("blink_me").removeClass("error");
				}else{
					tabs.getChildByIndex(index).addClass("blink_me").addClass("error");
					global.addAll(errors);
				}
				
			}
			index++;
			//	validators.putAll(form.getValidators());
		}
		
		
		
		
		
		if(global.size() > 0){
			throw new ValidationException(global);
		}
	}
	
	
	
	public BaseErpModel getData(){
		return data;
	}
	
	
	
	public BaseErpModel saveOrUpdate()throws ValidationException, Exception{
		validate();
		fillModel();
		if(isNew()){
			fireActionInterceptors(true, true, data);
			controller.insertRecord(data);
			fireActionInterceptors(false, true, data);
		}else{
			fireActionInterceptors(true, false, data);
			controller.updateRecord(data);
			fireActionInterceptors(false, false, data);
		}
		return data;
	}
	
	
	public void fireSetDataListener(BaseErpModel model,boolean pre){
		for(ActionInterceptor<?> i : interceptors){
			if(pre){
				i.preSetData(model, this);
			}else{
				i.postSetData(model, this);
			}
		}
	}
	public void setData(BaseErpModel model, boolean fireListener){
		if(fireListener)
			fireSetDataListener(model, true);
		this.data = model;
		for(Form f : forms){
			if(f != null)
				f.setData(model);
		}
		if(fireListener)
			fireSetDataListener(model, false);
	}
	
	public void setData(BaseErpModel model){
		setData(model,true);
	}
	
	public boolean isNew(){
		return data.getId() == null;
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return forms_.forms()[index].label();
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		
		Form form = forms.get(index);
		if(form == null){
			form = new Form(forms_.forms()[index], vo_, controller, index);
			if(data != null){
				
				form.setData(data);
			}
			forms.remove(index);
			forms.add(index, form);
		}
		return forms.get(index);
	}

	
	@Override
	public int getSelectedTab() {
		return 0;
	}
	

	@Override
	public int size() {
		return forms_.forms().length;
	}
}
