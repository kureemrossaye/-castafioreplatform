package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.ex.EXContainer;

public class ViewDate extends EXContainer  implements View   {

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	public final static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	
	public ViewDate(String field, Class<? extends BaseErpModel> vo) {
		super(field, "div");
		this.vo = vo;
		this.field = ReflectionUtils.findField(vo, field);
	}
	
	public void setData(BaseErpModel data){
		Date l = (Date)BeanUtil.getProperty(data, field.getName());
		if(l != null)
			setText(format.format(l));
		else
			setText("");
	}


	

}
