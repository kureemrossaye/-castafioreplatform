package org.castafiore.erp.ui.form.controls;

import java.util.Date;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class DateControl extends EXDatePicker implements InputControl<Date> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateControl(String name, Date date) {
		super(name, date);
	}

	public DateControl(String name) {
		super(name);
		setValue(new Date());
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
		setValue((Date)BeanUtil.getProperty(model, getProperty()));
		
	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("right")));
	}

}
