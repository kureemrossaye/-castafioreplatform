package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.ex.EXContainer;

public class ViewBoolean extends EXContainer implements View   {

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	
	
	public ViewBoolean(String field, Class<? extends BaseErpModel> vo) {
		super(field, "div");
		this.vo = vo;
		this.field = ReflectionUtils.findField(vo, field);
	}
	
	public void setData(BaseErpModel data){
		Boolean l = (Boolean)BeanUtil.getProperty(data, field.getName());
		if(l == null){
			setText("");
			return;
		}
		if(l)
			setText("True");
		else
			setText("False");
		
	}


	

}
