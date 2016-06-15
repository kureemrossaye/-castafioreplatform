package org.castafiore.erp.ui.form;

import java.util.LinkedList;
import java.util.List;

public class ValidationException extends org.castafiore.ui.ex.form.ValidationException{

	private List<Error> error = new LinkedList<Error>();
	
	public ValidationException(List<Error> error) {
		super();
		this.error = error;
	}

	public ValidationException(String message, Throwable cause,List<Error> error) {
		super(message, cause);
		this.error = error;
	}

	public ValidationException(String message,List<Error> error) {
		super(message);
		this.error = error;
	}

	public ValidationException(Throwable cause,List<Error> error) {
		super(cause);
		this.error = error;
	}

	public List<Error> getError() {
		return error;
	}

	public void setError(List<Error> error) {
		this.error = error;
	}

	
	
	
}
