package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Survey;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXSurveyModule extends AbstractEXModule{
	
	public EXSurveyModule() {
		super("EXSurvey", "Survey", "icon-wrench", new String[]{"Survey", "Survey"}, "12");
		
		GridFrame frame = new GridFrame(Survey.class);
		addChild(frame,"0:0");
		
		
	}
	
	
	

}
