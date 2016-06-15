package org.castafiore.erp.ui.form;

import java.math.BigDecimal;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Item;
import org.castafiore.erp.PriceList;
import org.castafiore.erp.inventory.InventoryManager;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl.OnSelectListener;
import org.castafiore.erp.ui.form.controls.editable.EditableControl;
import org.castafiore.erp.ui.grid.AssociationTable;
import org.castafiore.spring.SpringUtil;

public class SelectProductDecorator implements FieldDecorator, OnSelectListener<Item>{

	AssociationTable table = null;
	
	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		this.table = (AssociationTable)form;
		((MagicSelectControl<Item>)field).addOnSelectListener(this);
		
	}

	@Override
	public void onSelect(MagicSelectControl<? extends BaseErpModel> source,
			Item newValue) {
		
		Forms forms = table.getAncestorOfType(Forms.class);
		PriceList pl = (PriceList)forms.getField("priceList").getValue();
		
		
		BigDecimal price = SpringUtil.getBeanOfType(InventoryManager.class).getPrice(newValue.getId(), pl.getId());
		
		
		
		EditableControl ctr = source.getAncestorOfType(EditableControl.class);
		table.setValueAt(2, ctr.getRow(), 0, price);
		
	}

}
