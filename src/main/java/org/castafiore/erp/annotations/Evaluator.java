package org.castafiore.erp.annotations;

public @interface Evaluator {
	
	public String[] listen() default {};
	
	public Class<? extends org.castafiore.erp.ui.form.Evaluator> evaluator() default org.castafiore.erp.ui.form.Evaluator.class;

}
