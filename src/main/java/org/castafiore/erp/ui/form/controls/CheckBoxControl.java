package org.castafiore.erp.ui.form.controls;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;

public class CheckBoxControl extends EXCheckBox implements InputControl<Boolean> {

	public CheckBoxControl(String name) {

		super(name);

	}

	public CheckBoxControl(String name, String addon) {

		this(name);
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
		Object o = BeanUtil.getProperty(model, getName());
		setValue((Boolean)o);

	}

}
