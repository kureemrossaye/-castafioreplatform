package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.math.BigInteger;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.ex.EXContainer;

public class ViewInt extends EXContainer  implements View  {

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	
	
	public ViewInt(String field, Class<? extends BaseErpModel> vo) {
		super(field, "div");
		this.vo = vo;
		this.field = ReflectionUtils.findField(vo, field);
	}
	
	public void setData(BaseErpModel data){
		BigInteger l = (BigInteger)BeanUtil.getProperty(data, field.getName());
		if(l != null)
			setText(l.toString());
		else
			setText("");
	}


	

}
