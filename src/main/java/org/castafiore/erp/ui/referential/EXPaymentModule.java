package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.module.grids.EXPayment;
import org.castafiore.erp.ui.module.grids.EXPaymentType;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXPaymentModule extends AbstractEXModule{

	public EXPaymentModule() {
		super("EXPaymentModule", "Payment", "icon-dollar", new String[]{"Sales", "Payments"}, "12");
		
		
		EXTabPanel tabs = new EXTabPanel("tabs", new TabModel() {
			
			@Override
			public int size() {
				return 2;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				if(index == 0){
					return "Payments";
				}else{
					return "Payment type";
				}
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				if(index == 0){
					EXPayment payment = new EXPayment();
					return payment;
				}else{
					return new EXPaymentType();
				}
			}
			
			@Override
			public int getSelectedTab() {
				return 0;
			}
		});
		
		EXPanel panel = new EXPanel("ap");
		panel.setBody(tabs);
		addChild(panel,"0:0");
		
	}

}
