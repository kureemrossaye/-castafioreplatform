package org.castafiore.erp.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.castafiore.erp.ui.form.IGroup;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Group {
	
	public String name();
	
	public String label();
	
	public String[] fields() default {};
	
	public String layoutData()default "0:0";
	
	public DisplayType displayType() default DisplayType.FORM;
	
	public Class<? extends IGroup> impl () default org.castafiore.erp.ui.form.Group.class;
	
	public String style() default "";
	
	public boolean showTitle() default false;

}
