package org.castafiore.erp.ui;

import org.castafiore.erp.ui.security.EXLogin;
import org.castafiore.ui.ex.EXWebServletAwareApplication;

public class LoginApp extends EXWebServletAwareApplication {

	public LoginApp() {
		super("login");
		
		EXLogin login = new EXLogin("login");
		addChild(login);
	}

}
