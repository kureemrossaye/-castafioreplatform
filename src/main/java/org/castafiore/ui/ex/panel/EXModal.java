package org.castafiore.ui.ex.panel;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.EXContainer;

public class EXModal extends EXContainer implements Panel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Container closeButton = new EXContainer("close", "button").addClass("close").setAttribute("data-dismiss", "modal").setAttribute("aria-label", "Close").setText("<span aria-hidden=\"true\">&times;</span>");
	
	private Container modalTitle = new EXContainer("title", "h4").addClass("modal-title");
	
	
	private Container modalHeader = new EXContainer("modalHeader", "div").addClass("modal-header");
	
	
	private Container modalBody = new EXContainer("modalBody", "div").addClass("modal-body");
	
	
	private Container modalFooter = new EXContainer("modalFooter", "div").addClass("modal-footer");
	
	private Container modalDialog = new EXContainer("modalDialog", "div").addClass("modal-dialog");
	
	public static String SIZE_SMALL="btn-sm";
	public static String SIZE_LARGE="btn-lg";

	public static String[] SIZES = new String[]{SIZE_SMALL,SIZE_LARGE};
	
	
	public EXModal(String name, String title) {
		super(name, "div");
		addClass("modal").addClass("fade");
		setAttribute("tabindex", "-1");
		setAttribute("role", "dialog");
		
		
		addChild(modalDialog);
		
		Container modalContent = new EXContainer("modalContent", "div").addClass("modal-content");
		
		modalDialog.addChild(modalContent);
		
		modalContent.addChild(modalHeader);
		
		modalHeader.addChild(closeButton).addChild(modalTitle);
		
		modalTitle.setText(title);
		
		modalContent.addChild(modalBody);
		
		modalContent.addChild(modalFooter);
		close();
		
	}
	
	public EXModal setSize(String size){
		for(String s : SIZES){
			modalDialog.removeClass(s);
		}
		
		modalDialog.addClass(size);
		
		return this;
	}

	@Override
	public Panel setTitle(String title) {
		modalTitle.setText(title);
		return this;
	}

	@Override
	public Panel setBody(Container container) {
		modalBody.getChildren().clear();
		modalBody.setRendered(false);
		modalBody.addChild(container);
		return this;
	}

	@Override
	public Container getBody() {
		return modalBody.getChildByIndex(0);
	}

	@Override
	public Panel setShowHeader(boolean showHeader) {
		modalHeader.setDisplay(showHeader);
		return this;
	}

	@Override
	public Panel setShowFooter(boolean display) {
		modalFooter.setDisplay(display);
		return this;
	}

	@Override
	public Panel setShowCloseButton(boolean b) {
		closeButton.setDisplay(b);
		return this;
	}
	
	public Container getDialog(){
		return modalDialog;
	}
	
	public Container getFooter(){
		return modalFooter;
	}
	
	public void decorateAsToggleButton(Container closeButton){
		closeButton.setAttribute("data-toggle", "modal");
		closeButton.setAttribute("data-target", "#" + getId());
	}
	
	public void close(){
		JQuery jquery = getJQuery();
		addCommand(jquery.invoke("modal","hide"));
	}
	
	public void open(){
		JQuery jquery = getJQuery();
		addCommand(jquery.invoke("modal", "show"));
	}

}
