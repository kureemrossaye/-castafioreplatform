package org.castafiore.erp.ui.form;

import java.util.ArrayList;
import java.util.List;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.grid.GridDataLocator;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;

public class EmbededForm<T extends BaseErpModel> extends EXContainer implements InputControl<T>{
	
	private Forms forms;
	
	
	
	private Class<? extends BaseErpModel> vo;
	public EmbededForm(String name, Class<? extends BaseErpModel> vo, GridController controller) {
		super(name, "div");
		this.vo = vo;
	
		forms = new Forms(ReflectionUtils.getAnnotation(vo,org.castafiore.erp.annotations.Forms.class), vo, controller);
		addChild(forms);
		
		if(forms.getModel().size() == 1){
			forms.setShowTabs(false);
		}
	}

	@Override
	public T getValue() {
		forms.fillModel();
		return (T)forms.getData();
	}

	@Override
	public void setValue(T value) {
		if(value == null){
			try{
				forms.setData(vo.newInstance());
			}catch(Exception e){
				throw new UIException(e);
			}
		}else{
			forms.setData(value);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		forms.setEnabled(enabled);
	}

	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		BaseErpModel property = getValue();
		try{
			forms.saveOrUpdate();
		}catch(ValidationException ve){
			throw ve;
		}catch(Exception e){
			Error error = new Error(ReflectionUtils.findField(vo, getProperty()), "error while saving " + getProperty());
			List<Error> errors = new ArrayList<Error>(1);
			errors.add(error);
			throw new ValidationException(e, errors);
		}
		 
		BeanUtil.setProperty(model, getProperty(), property);
	}

	@Override
	public void fillControl(BaseErpModel model) {
		Object val = BeanUtil.getProperty(model, getProperty());
		
		setValue((T)val);
	}

}
