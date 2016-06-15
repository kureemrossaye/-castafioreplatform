package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.ex.form.button.EXButtonSet;

public interface FieldAction {
	
	public EXButtonSet getButtons(InputControl<?> field, IGroup group, Class<? extends BaseErpModel> vo);
	
	public void changeMode(EXButtonSet buttons, ActionScope scope, InputControl<?> field, IGroup group, Class<? extends BaseErpModel> vo);

}
