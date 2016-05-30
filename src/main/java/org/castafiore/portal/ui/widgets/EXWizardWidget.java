package org.castafiore.portal.ui.widgets;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXWizardWidget extends EXWidget implements Event {

	private Container steps = new EXContainer("steps", "ul").addClass("hormenu");

	private Container stepsContainer = new EXContainer("stepsContainer", "div").addClass("stepContainer");

	private Container wizardForm = new EXContainer("wizardForm", "div").addClass("stdform");

	private Container actionBar = new EXContainer("actionBar", "div").addClass("actionBar");
	
	private boolean enableFinishButton = false; 
	
	private List<Integer> errorSteps = new LinkedList<Integer>();
	
	private String labelNext = "Next";
	
	private String labelPrevious = "Previous";
	
	private String labelFinish = "Finish";
	
	private boolean includeFinishButton = true;

	private List<WizardEventHandler> handlers = new LinkedList<WizardEventHandler>();
	
	private Container previous = new EXContainer("previous", "a").setText(labelPrevious).addClass("buttonPrevious");
	
	private Container next = new EXContainer("next", "a").setText(labelNext).addClass("buttonNext");
	
	private Container finish = new EXContainer("previous", "a").setText(labelFinish).addClass("buttonFinish");
	
	private Integer currentStep = -1;

	public EXWizardWidget(String name, String title) {
		super(name, title);
		setBody(wizardForm);
		Container wizard = new EXContainer("wizard", "div").addClass("wizard");
		wizard.addChild(new EXContainer("", "br"));
		wizard.addChild(steps);
		wizard.addChild(stepsContainer);
		wizard.addChild(actionBar);
		wizardForm.addChild(wizard);
		
		actionBar.addChild(finish.addEvent(this, CLICK)).addChild(next.addEvent(this, CLICK)).addChild(previous.addEvent(this, CLICK));
		
		if(!includeFinishButton){
			finish.setDisplay(false);
		}
	}
	
	public EXWidget getStep(int index){
		return (EXWidget)stepsContainer.getChildByIndex(index);
	}
	
	
	public void finish(){
		for(WizardEventHandler handler : handlers){
			handler.onFinish();
		}
		
		move(steps.getChildren().size()-currentStep);
	}
	
	public void reset(){
		currentStep = -1;
		move(0);
	}
	
	public void move(int iSteps){
		
		EXWidget leaving = null;
		if(currentStep >=0 && currentStep < steps.getChildren().size()){
			leaving = (EXWidget)stepsContainer.getChildByIndex(currentStep);
		}
		
		currentStep = currentStep + iSteps;
		
		if(currentStep >= steps.getChildren().size()+1 || currentStep <= -1){
			currentStep = currentStep - iSteps;
			return;
		}
		
		EXWidget showing = null;
		if(currentStep >=0 && currentStep < steps.getChildren().size()){
			showing = (EXWidget)stepsContainer.getChildByIndex(currentStep);
		}
		
		for(WizardEventHandler handler : handlers){
			if(leaving != null)
				handler.onLeaveStep(currentStep-iSteps, leaving);
			if(showing != null)
				handler.onShowStep(currentStep, showing);
		}
		int index = 0;
		for(Container c : steps.getChildren()){
			c.removeClass("selected");
			if(index == currentStep){
				c.addClass("selected");
			}
			index++;
		}
		
		index = 0;
		for(Container c : stepsContainer.getChildren()){
			c.setDisplay(false);
			if(index == currentStep){
				c.setDisplay(true);
			}
			index++;
		}
		
		if(currentStep == 0){
			previous.addClass("buttonDisabled");
			next.removeClass("buttonDisabled");
			finish.removeClass("buttonDisabled");
		}
		
		if(currentStep == stepsContainer.getChildren().size() -1){
			previous.removeClass("buttonDisabled");
			next.addClass("buttonDisabled");
			finish.removeClass("buttonDisabled");
		}else{
			previous.removeClass("buttonDisabled");
			next.removeClass("buttonDisabled");
			finish.removeClass("buttonDisabled");
		}
		
	}
	
	public EXWizardWidget addEventHandler(WizardEventHandler handler){
		handlers.add(handler);
		return this;
	}
	
	public List<WizardEventHandler> getEventHandlers(){
		return handlers;
	}

	public EXWizardWidget addStep(String label, EXWidget widget) {

		Container li = new EXContainer("li", "li");

		String txt = "<a href=\"#&\"><span class=\"h2\">Step $</span><span class=\"label\">£</span></a>";

		int index = steps.getChildren().size() + 1;

		txt = txt.replace("$", index + "").replace("&", widget.getId()).replace("£", label);

		li.setText(txt);

		steps.addChild(li);

		widget.addClass("formwiz").addClass("content");

		stepsContainer.addChild(widget);

		return this;
	}

	public Container getStepsContainer() {
		return stepsContainer;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {

		if(!container.getStyleClass().contains("buttonDisabled")){
			if(container.equals(next)){
				move(1);
			}else if(container.equals(previous)){
				move(-1);
			}else{
				finish();
			}
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {

	}

	public interface WizardEventHandler {
		
		public void onLeaveStep(Integer step, EXWidget widget);

		public void onShowStep(Integer step, EXWidget widget);

		public void onFinish();
		
	}

}
