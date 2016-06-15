package org.castafiore.erp.ui.form;

import org.castafiore.erp.ui.form.controls.InputControl;

public interface FieldDecorator {
	
	public void decorateField(InputControl<?> field, IGroup form);

}
