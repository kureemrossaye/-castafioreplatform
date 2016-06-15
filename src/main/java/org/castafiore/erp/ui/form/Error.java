package org.castafiore.erp.ui.form;

import java.lang.reflect.Field;

import org.castafiore.erp.ui.form.validation.ValidationUtil;

public class Error {
	private Field field;
	
	private String message;

	
	
	public Error(Field field, String defaultErrorMsg) {
		super();
		this.field = field;
		this.message = ValidationUtil.getErrorMsg(field, defaultErrorMsg);
	}
	

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
