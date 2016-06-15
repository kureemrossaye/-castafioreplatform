package org.castafiore.erp.ui.form;

import groovy.transform.Field;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.InputControl;

public interface Evaluator<T> {

	public void evaluate(InputControl<T> input, Field prop,String field, Class<? extends BaseErpModel> vo, BaseErpModel model);

}
