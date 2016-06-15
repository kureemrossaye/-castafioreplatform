package org.castafiore.erp.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

	public String[] columns();

	public boolean showHeader() default true;

	public boolean showFooter() default true;

	public boolean showToolbar() default true;

	public boolean showColumnHeaders() default true;

	public boolean showLineNumbers() default true;

	public int pageSize() default 20;

}
