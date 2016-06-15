package org.castafiore.erp.ui.form;

import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.Container;

public interface IGroup extends Container{
	
	public InputControl<?> getField(String name);
	
	public void fillModel(final BaseErpModel model);
	public void setData(final BaseErpModel model);
	
	public Map<InputControl<?>, List<String>> getValidators();
	
	public void setEnabled(boolean b);
	
	public void addValidator(InputControl<?> input, String validator);
}
