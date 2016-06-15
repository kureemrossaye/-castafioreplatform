package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;

import org.castafiore.erp.ui.form.Error;

public interface Validator {
	
	public String getName();
	
	public Error validate(Field field, Object value);

}
