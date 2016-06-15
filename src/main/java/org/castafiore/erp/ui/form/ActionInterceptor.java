package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;

/**
 * 
 * @author acer
 *
 * @param <T>
 */
public interface ActionInterceptor<T extends BaseErpModel> {
	
	public void preSave(BaseErpModel instance, Forms forms);
	
	public void postSave(BaseErpModel instance ,Forms forms);
	
	public void preUpdate(BaseErpModel instance, Forms forms);
	
	public void postUpdate(BaseErpModel instance, Forms forms);
	
	public void preSetData(BaseErpModel data, Forms forms);
	
	public void postSetData(BaseErpModel data, Forms forms);

}
