package org.castafiore.erp.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Form {
	
	public String name();
	
	public String label();
	
	public String layout() default "12";
	
	public Group[] groups() default {};
	
	public String style() default "";
	
	public boolean lazy() default false;
	
	
	

}
