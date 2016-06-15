package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;

public class AbstractActionInterceptor<T extends BaseErpModel> implements ActionInterceptor<T>{

	@Override
	public void preSave(BaseErpModel instance, Forms forms) {}

	@Override
	public void postSave(BaseErpModel instance, Forms forms) {}

	@Override
	public void preUpdate(BaseErpModel instance, Forms forms) {}

	@Override
	public void postUpdate(BaseErpModel instance, Forms forms) {}

	@Override
	public void preSetData(BaseErpModel data, Forms forms) {}

	@Override
	public void postSetData(BaseErpModel data, Forms forms) {}

}
