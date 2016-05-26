package org.castafiore.portal.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXProgressBar;

public class EXPlainWidget extends EXContainer{

	private Container topLabel = new EXContainer("small", "small");
	
	private Container bottomLabel =new EXContainer("small", "small").setStyle("font-weight", "bold");
	
	private EXProgressBar progressBar = new EXProgressBar("bar");
	
	public EXPlainWidget(String name) {
		super(name, "div");
		addClass("plainwidget");
		
		
		addChild(topLabel);
		progressBar.getBar().setStyleClass("bar");
		progressBar.addClass("progress-info");
		addChild(progressBar);
		
		
		addChild(bottomLabel);
		
	}
	
	public EXPlainWidget setProgressValue(Integer value){
		progressBar.setValue(value);
		return this;
	}
	
	public EXPlainWidget setTopLabel(String text){
		topLabel.setText(text);
		return this;
	}
	
	public EXPlainWidget setBottomLabel(String text){
		bottomLabel.setText(text);
		return this;
	}
	
	

}
