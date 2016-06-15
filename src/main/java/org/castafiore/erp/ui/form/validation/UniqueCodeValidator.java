package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;

import org.castafiore.erp.ui.form.Error;


public class UniqueCodeValidator implements Validator{
	
	

	@Override
	public String getName() {
		return "unique";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Error validate(Field field, Object value) {
			Error error = ValidationUtil.REQUIRED.validate(field, value);
			if(error == null){
				Class clazz = field.getDeclaringClass();
				if(!ValidationUtil.checkUnique(value.toString(), field, clazz)){
					error = new Error(field, "The field {caption} must be unique");
				}
			}
			return error;
		
		
	}

}
