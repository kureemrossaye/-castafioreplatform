package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Task;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXTasksModule extends AbstractEXModule{

	public EXTasksModule() {
		super("Tasks", "Tasks", "",new String[]{}, "12");
		
		GridFrame frame = new GridFrame(Task.class);
		addChild(frame, "0:0");
	}

}
