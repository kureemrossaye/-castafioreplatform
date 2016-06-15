package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Account;
import org.castafiore.erp.AccountingMotive;
import org.castafiore.erp.LedgerType;
import org.castafiore.erp.VatRegisterType;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXAccountingReferentialModule extends AbstractEXModule{

	public EXAccountingReferentialModule() {
		super("EXAccountingReferentialModule", "Configurations", "icon-wrench", new String[]{"Accounting", "Configuration"}, "12");
		
		
		final GridFrame account = new GridFrame(Account.class);
		final GridFrame ledgerType = new  GridFrame(LedgerType.class);
		final GridFrame motives = new GridFrame(AccountingMotive.class);
		final GridFrame vatRegisterType = new GridFrame(VatRegisterType.class);
		
		EXTabPanel panel = new EXTabPanel("panel",new TabModel() {
			
			@Override
			public int size() {
				return 4;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				if(index == 0){
					return "Chart of account";
				}else if(index == 1){
					return "Ledger type";
				}else if(index == 2){
					return "Vat register type";
				}else{
					return "Account motives";
				}
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				if(index == 0){
					return account;
				}else if (index == 1){
					return ledgerType;
				}else if (index == 2){
					return vatRegisterType;
				}else{
					return motives;
				}
			}
			
			@Override
			public int getSelectedTab() {
				return 0;
			}
		});
		
		
		addChild(panel,"0:0");
		
		
		
		
	}

}
