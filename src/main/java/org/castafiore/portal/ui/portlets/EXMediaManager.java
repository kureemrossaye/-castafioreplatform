package org.castafiore.portal.ui.portlets;

import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mediaManager")
@Scope("prototype")
public class EXMediaManager extends EXXHTMLFragment{

	public EXMediaManager() {
		super("EXMediaManager", "templates/EXMediaManager.xhtml");
		addClass("mediamgr");
	}
}
