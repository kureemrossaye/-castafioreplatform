package org.castafiore.portal.widgets;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.castafiore.ui.ex.EXContainer;

public class EXDateWidget extends EXContainer{

	
	
	public EXDateWidget( String name) {
		super(name, "div");
		addClass("datewidget");
	}
	
	
	public EXDateWidget setDate(Date date){
		setText("Today is " + new SimpleDateFormat("DDDD, MM dd, yyyy hh:mm").format(date));
		return this;
	}

}
