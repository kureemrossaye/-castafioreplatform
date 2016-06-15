package org.castafiore.erp.ui.form.controls;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class TextAreaControl extends EXTextArea implements InputControl<String> {

	public TextAreaControl(String name) {

		super(name, "div");
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
		if(o== null){
			o="";
		}
		setValue(o.toString());

	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("right")));
	}

}
