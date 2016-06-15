package org.castafiore.erp.ui.configs;

import java.util.Map;

import org.castafiore.erp.ConfigModel;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.HelpUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;

public class UIConfig extends EXContainer implements Event {
	private Container help = new EXContainer("help", "p").addClass("UIConfig-Help");
	
	private  Class<? extends ConfigModel> vo;
	
	
	public UIConfig(Class<? extends ConfigModel> vo) {
		super(vo.getClass().getSimpleName(), "div");
		this.vo = vo;
		
		String helpContent = HelpUtil.getHelp(vo);
		help.setText(helpContent);
		addChild(help);
		EXButton close = new EXButton("close", "Close");
		close.addEvent(this, CLICK);
		close.addClass("pull-right");
		help.addChild(close);
		addClass("configtable");
		addClass("w2ui-group").addClass("ui-widget-content").setStyle("width", "500px").setStyle("padding", "14px");
		ConfigTable table = new ConfigTable( vo, new GridController());
		addChild(table);

		
		setStyle("position", "absolute").setStyle("top", "10px").setStyle("left", "40px").setStyle("z-index", "8");
	}


	@Override
	public void ClientAction(JQuery container) {
		container.append(new JQuery("#" + getId()).fadeOut(100));
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		this.remove();
		return false;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
		
	}

	
	

	
	

}
