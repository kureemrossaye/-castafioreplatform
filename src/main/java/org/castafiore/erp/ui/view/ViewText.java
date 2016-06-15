package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.ex.EXContainer;

public class ViewText extends EXContainer  implements View  {

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	
	
	public ViewText(String field, Class<? extends BaseErpModel> vo) {
		super(field, "div");
		this.vo = vo;
		this.field = ReflectionUtils.findField(vo, field);
	}
	
	public void setData(BaseErpModel data){
		Object l = (String)BeanUtil.getProperty(data, field.getName());
		if(l != null)
			setText(l.toString());
		else
			setText("");
	}


	

}
