package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.PriceList;
import org.castafiore.erp.Supplier;
import org.castafiore.erp.Vat;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl.OnSelectListener;

public class SupplierFieldDecorator implements FieldDecorator , OnSelectListener<Supplier>{

	IGroup form_ = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		form_ = form;
		((MagicSelectControl<Supplier>)field).addOnSelectListener(this);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSelect(MagicSelectControl<? extends BaseErpModel> source,
			Supplier newValue) {
		PriceList pl = newValue.getPriceList();
		((MagicSelectControl<PriceList>) form_.getField("priceList")).setValue(pl);
		
		Vat defaultVat = newValue.getVat();
		((MagicSelectControl<Vat>) form_.getAncestorOfType(Form.class).getGroups().get(1).getField("vat")).setValue(defaultVat);
		
	}
	
}
