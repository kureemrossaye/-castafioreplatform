package org.castafiore.erp.ui.form.controls;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class SimpleSelectControl<T> extends EXSelect<T> implements InputControl<T> {

	public SimpleSelectControl(String name, DataModel<T> model) {

		super(name, model);
		addClass("input-group");

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
		setValue((T)o);

	}
	
	

}
