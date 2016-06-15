package org.castafiore.erp.ui.form.controls;

import java.math.BigInteger;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.AbstractStatefullComponent;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class IntegerControl extends AbstractStatefullComponent<BigInteger> implements InputControl<BigInteger>{

	public IntegerControl(String name) {
		super(name, "input");
		setReadOnlyAttribute("type", "text");
		setValue(BigInteger.ZERO);
		addClass("input-group").addClass("form-control");
		setStyle("text-align", "right");
	}

	@Override
	public BigInteger getValue() {
		try{
			return new BigInteger(getAttribute("value"));
		}catch(Exception e){
			return BigInteger.ZERO;
		}
	}

	@Override
	public void setValue(BigInteger value) {
		if(value == null){
			setAttribute("value", "0");
		}else{
		setAttribute("value", value.toString());
		}
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(enabled){
			setAttribute("disabled", (String)null);
		}else{
			setAttribute("disabled", "disabled");
		}
		
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
		setValue((BigInteger)BeanUtil.getProperty(model, getProperty()));
		
	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		query.invoke("forceNumeric", new JMap().put("allowDecimal", false));
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("right")));
	}
	
}
