package org.castafiore.erp.ui.form.controls;

import java.math.BigDecimal;

import org.castafiore.utils.StringUtil;

public class CurrencyControl extends DecimalControl{

	public CurrencyControl(String name) {
		super(name);
	}

	
	@Override
	public void setValue(BigDecimal value) {
		if(value == null){
			setAttribute("value", "0.00");
		}else{
			setAttribute("value", StringUtil.toCurrency("", value).trim());
		}
		
	}
}
