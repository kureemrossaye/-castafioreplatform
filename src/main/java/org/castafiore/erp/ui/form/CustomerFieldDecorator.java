package org.castafiore.erp.ui.form;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Customer;
import org.castafiore.erp.PriceList;
import org.castafiore.erp.Vat;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl;
import org.castafiore.erp.ui.form.controls.MagicSelectControl.OnSelectListener;

/**
 * this will be used when selecting a customer in sales order
 */
public class CustomerFieldDecorator implements FieldDecorator , OnSelectListener<Customer>{

	IGroup form_ = null;
	
	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		form_ = form;
		((MagicSelectControl<Customer>)field).addOnSelectListener(this);
		
	}


	@Override
	public void onSelect(MagicSelectControl<? extends BaseErpModel> source,
			Customer newValue) {
		PriceList pl = newValue.getPaymentTerms().getPriceList();
		((MagicSelectControl<PriceList>) form_.getField("priceList")).setValue(pl);
		
		Vat defaultVat = newValue.getVat();
		((MagicSelectControl<Vat>) form_.getAncestorOfType(Form.class).getGroups().get(1).getField("vat")).setValue(defaultVat);
		
	}

	
	

}
