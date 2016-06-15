package org.castafiore.erp.ui.referential;

import org.castafiore.erp.AdjustmentType;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ItemGroup;
import org.castafiore.erp.ItemType;
import org.castafiore.erp.MeasureUnit;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXInventoryReferentialModule extends AbstractEXModule implements TabModel{
	
	private static String[] INVENTORY_LABELS = new String[] {"Item group", "Item type",	"Adjustment type", "Measure unit"};

	private static Class<? extends BaseErpModel>[] INVENTORY = new Class[] {ItemGroup.class, ItemType.class, AdjustmentType.class,MeasureUnit.class };
	
	
	public EXInventoryReferentialModule() {
		super("EXInventoryReferentialModule", "Configurations", "icon-wrench",new String[] {"Inventory",  "Configuration" }, "12");
		EXTabPanel panel = new EXTabPanel("panel", this);

		addChild(panel, "0:0");
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return INVENTORY_LABELS[index];
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {

		
		GridFrame ref = new GridFrame(INVENTORY[index]);
		
		

		return ref;
	}

	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public int size() {
		return INVENTORY_LABELS.length;
	}
}
