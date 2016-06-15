package org.castafiore.erp.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.FieldAction;
import org.castafiore.erp.ui.form.FieldDecorator;
import org.castafiore.erp.ui.form.NoFieldAction;
import org.castafiore.erp.ui.form.NoInterceptor;
import org.castafiore.erp.ui.grid.DefaultDataLocator;
import org.castafiore.erp.ui.grid.GridDataLocator;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Field {

	public String caption() default "";

	public int min() default Integer.MAX_VALUE;
	
	public int max() default Integer.MIN_VALUE;

	public String attr() default "";
	
	public FieldType type() default FieldType.alphaNumeric;
	
	public boolean required() default false;
	
	public Item[] items() default {};
	
	public Class<?> lookupModel() default BaseErpModel.class;
	
	public int size() default -1;;
	
	public int maxLength() default -1;
	
	public String style() default "width:100%";
	
	public boolean editable() default true;
	
	//public String colWidth() default "20%";
	
	public String labelWidth() default "20%";
	
	public String fieldWidth() default "80%";
	
	public Class<? extends FieldDecorator> fieldInterceptor() default NoInterceptor.class; 
	
	public boolean updateable() default true;
	
	public String format() default "";
	/**
	 * need to listen to dependent fields
	 * =SUM(lines.total)
	 * =SUM(${lined.total}) * vat.deductible
	 */
	public String formula() default "";
	
	
	public String errorMsg() default"";
	
	
	public String[] validators()default {};
	
	public Class<? extends FieldAction> actions() default NoFieldAction.class;
	
	public ActionScope actionScope() default ActionScope.create;
	
	public int[] status() default{};
	
	public Class<? extends GridDataLocator> dataLocator() default DefaultDataLocator.class;
	
}
