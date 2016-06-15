package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.castafiore.erp.ui.form.Error;

public class EmailValidator implements Validator{

	@Override
	public String getName() {
		return "email";
	}

	@Override
	public Error validate(Field field, Object value) {
		Error err = ValidationUtil.REQUIRED.validate(field, value);
		if(err == null){
			
			if(!ValidationUtil.isValidEmailAddress(value.toString())){
				return new Error(field, "The field {caption} should be a valid email address");
			}
		}
		return null;
	}
	
	

}
