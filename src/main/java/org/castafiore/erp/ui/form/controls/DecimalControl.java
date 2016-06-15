package org.castafiore.erp.ui.form.controls;

import java.math.BigDecimal;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.AbstractStatefullComponent;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class DecimalControl extends AbstractStatefullComponent<BigDecimal> implements InputControl<BigDecimal>{

	public DecimalControl(String name) {
		super(name, "input");
		setReadOnlyAttribute("type", "text");
		setValue(BigDecimal.ZERO);
		addClass("form-control").addClass("form-control");
		setStyle("text-align", "right");
	}

	@Override
	public BigDecimal getValue() {
		try{
			return new BigDecimal(getAttribute("value"));
		}catch(Exception e){
			return BigDecimal.ZERO;
		}
	}

	@Override
	public void setValue(BigDecimal value) {
		if(value == null){
			setAttribute("value", "0");
		}else{
			setAttribute("value", value.toPlainString());
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
		setValue((BigDecimal)BeanUtil.getProperty(model, getProperty()));
		
	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		query.invoke("forceNumeric", new JMap().put("allowDecimal", true).put("allowNegative", true).put("decimalPlaces", 2));
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("right")));
		
	}

}
