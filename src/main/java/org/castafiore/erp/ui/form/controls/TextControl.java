package org.castafiore.erp.ui.form.controls;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class TextControl extends EXMaskableInput implements InputControl<String> {

	public TextControl(String name) {

		super(name);
		addClass("input-group");

	}

	public TextControl(String name, String addon) {

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
		if(o == null){
			setValue("");
		}else{
			setValue(o.toString());
		}

	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("bottom")));
	}

}