package org.castafiore.erp.ui.form.controls;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class LabelControl extends EXLabel implements InputControl<String>{

	
	 
	public LabelControl(String name, String value) {
		super(name, value);
	}

	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		Field f = org.castafiore.erp.utils.ReflectionUtils.findField(model.getClass(), getProperty());
		String val = getValue();
		if(f.getType().isAssignableFrom(Boolean.class)){
			BeanUtil.setProperty(model, getProperty(), new Boolean(val));
			
		}else if(f.getType().isAssignableFrom(BigInteger.class)){
			BeanUtil.setProperty(model, getProperty(), new BigInteger(val));
		}else if(f.getType().isAssignableFrom(BigDecimal.class)){
			BeanUtil.setProperty(model, getProperty(), new BigDecimal(val));
		}else if(f.getType().isAssignableFrom(Date.class)){
			BeanUtil.setProperty(model, getProperty(), new Date(val));
		}else{
			BeanUtil.setProperty(model, getProperty(), getValue());
		}
		
		
	}

	@Override
	public void fillControl(BaseErpModel model) {
		Object val = BeanUtil.getProperty(model, getProperty());
		if(val != null)
			setValue(BeanUtil.getProperty(model, getProperty()).toString());
		else
			setValue("");
		
	}
	
	public void onReady(JQuery query){
		super.onReady(query);
		//query.invoke("bt", new JMap().put("trigger",  new JArray().add("mouseover").add("mouseout")).put("position", new JArray().add("right")));
	}
}
