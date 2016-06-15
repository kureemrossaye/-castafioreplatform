package org.castafiore.erp.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Column {

	public String caption() default "";

	public String field() default "";

	public String size() default "";

	public int min() default -1;

	public int max() default -1;

	public int gridMinWidth() default -1;

	public boolean hidden() default false;

	public boolean sortable() default false;

	public boolean searchable() default false;

	public boolean resizable() default false;

	public String attr() default "";

	public String style() default "";

	public String render() default "";

	public String title() default "";

	public String formula() default "";
	
	public boolean editable() default true;

}
