package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;

import org.castafiore.erp.ui.form.Error;

public class RequiredValidator implements Validator{

	@Override
	public String getName() {
		return "required";
	}

	@Override
	public Error validate(Field field, Object value) {
		org.castafiore.erp.annotations.Field ann = field.getAnnotation(org.castafiore.erp.annotations.Field.class);
		
		if(value == null || (field.getType().equals(String.class) && value.toString().trim().length() == 0)){
			Error error = new Error(field, (ann != null &&ann.errorMsg().length() >0)? ann.errorMsg():"The field " + ann.caption() + " is required");
			return error;
		}
		
		return null;
		
	}

}
