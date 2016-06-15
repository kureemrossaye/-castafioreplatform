package org.castafiore.erp.ui.referential;

import org.castafiore.erp.AccountingMotive;
import org.castafiore.erp.Bank;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ChargeType;
import org.castafiore.erp.Discount;
import org.castafiore.erp.LedgerType;
import org.castafiore.erp.PaymentType;
import org.castafiore.erp.Vat;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXAccountingSettings extends AbstractEXModule implements TabModel{

	private static String[] LABELS = new String[] {"Banks", "Vat",	"Discounts", "Payment types", "Accounting motives", "Charge types","Ledger types"};

	private static Class<? extends BaseErpModel>[] CLAZZ = new Class[] {Bank.class, Vat.class, Discount.class,PaymentType.class , AccountingMotive.class, ChargeType.class, LedgerType.class};
	
	
	public EXAccountingSettings() {
		super("EXAccountingSettings", "Configurations", "icon-wrench",new String[] {"Inventory",  "Configuration" }, "12");
		EXTabPanel panel = new EXTabPanel("panel", this);

		addChild(panel, "0:0");
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return LABELS[index];
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {

		
		GridFrame ref = new GridFrame(CLAZZ[index]);
		
		

		return ref;
	}

	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public int size() {
		return LABELS.length;
	}
	
}
