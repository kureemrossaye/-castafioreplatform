package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Account;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXAccountsModule extends AbstractEXModule{

	public EXAccountsModule() {
		super("Account module", "Accounts", "accounts.png", new String[]{"Finance", "Accounts"}, "12");
		
		GridFrame frame = new GridFrame(Account.class);
		addChild(frame, "0:0");
	}

}
