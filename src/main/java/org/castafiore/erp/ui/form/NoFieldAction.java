package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.ex.form.button.EXButtonSet;

public class NoFieldAction implements FieldAction{

	@Override
	public EXButtonSet getButtons(InputControl<?> field, IGroup group, Class<? extends BaseErpModel> vo) {
		return null;
	}

	@Override
	public void changeMode(EXButtonSet buttons, ActionScope scope,
			InputControl<?> field, IGroup group,
			Class<? extends BaseErpModel> vo) {
		
	}

}
