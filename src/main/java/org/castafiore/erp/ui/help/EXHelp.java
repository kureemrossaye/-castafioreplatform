package org.castafiore.erp.ui.help;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.utils.HelpUtil;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.js.JMap;

public class EXHelp extends EXButton{

	private String content;
	
	
	public EXHelp(Class<? extends BaseErpModel> clazz) {
		super("help","");
		content = HelpUtil.getHelp(clazz);
		
	}
	
	
	public void onReady(JQuery query){
		query.invoke("balloon", new JMap().put("contents", content).put("position","bottom"));
	}

}
