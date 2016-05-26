package org.castafiore.portal;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXPortal extends EXContainer {

	private EXLeftPanel leftPanel = new EXLeftPanel("leftPanel");

	private EXRightPanel rightPanel = new EXRightPanel("rightPanel");

	public EXPortal(String name) {
		super(name, "div");
		addClass("mainwrapper").addClass("fullwrapper");
		addChild(leftPanel);
		addChild(rightPanel);

	}

	public Container getLeftPanel() {
		return leftPanel;
	}

	public Container getRightPanel() {
		return rightPanel;
	}

	
	public EXPortal setBody(String title, String desc, Container body){
		
		rightPanel.setBody(title, desc, body);
		return this;
	}
}
