package org.castafiore.erp.ui.form.controls;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.StatefullComponent;

public interface InputControl<T> extends StatefullComponent<T>{
	
	public String getProperty();
	
	
	public void fillVo(BaseErpModel model);
	
	public void fillControl(BaseErpModel model);

}
