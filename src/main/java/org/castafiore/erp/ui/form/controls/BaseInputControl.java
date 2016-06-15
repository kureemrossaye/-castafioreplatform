package org.castafiore.erp.ui.form.controls;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.ex.form.EXMaskableInput;

public class BaseInputControl extends EXMaskableInput implements InputControl<String>{

	

	public BaseInputControl(String name, String value, String mask) {
		super(name, value, mask);
	}

	public BaseInputControl(String name, String value) {
		super(name, value);
	}

	public BaseInputControl(String name) {
		super(name);
	}

	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		BeanUtil.setProperty(model, getProperty(), getValue());
		
	}

	@Override
	public void fillControl(BaseErpModel model) {
		Object  o = BeanUtil.getProperty(model, getProperty());
		setValue(o.toString());
		
	}

}
