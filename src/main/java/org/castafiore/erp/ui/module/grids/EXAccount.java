package org.castafiore.erp.ui.module.grids;

import org.castafiore.erp.Account;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXAccount extends GridFrame{

	public EXAccount(Class<? extends BaseErpModel> voClass, String voTitle,
			boolean doubleColumnInForm) {
		super(Account.class);
	
	}
	
	

}
