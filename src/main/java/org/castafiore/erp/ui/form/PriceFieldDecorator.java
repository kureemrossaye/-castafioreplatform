package org.castafiore.erp.ui.form;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.erp.Customer;
import org.castafiore.erp.Item;
import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.Vat;
import org.castafiore.erp.sales.SalesManager;
import org.castafiore.erp.ui.form.controls.DecimalControl;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.editable.EditableControl;
import org.castafiore.erp.ui.grid.AssociationTable;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;

public class PriceFieldDecorator implements FieldDecorator, Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IGroup group_;

	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		field.addEvent(this, CHANGE);
		this.group_ = form;
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		@SuppressWarnings("rawtypes")
		EditableControl ctr = container.getAncestorOfType(EditableControl.class);
		
		SalesOrder tmp = new SalesOrder();
		group_.fillModel(tmp);
		
		BigDecimal quantity = container.getAncestorOfType(DecimalControl.class).getValue();
		BigDecimal price = (BigDecimal)((AssociationTable)group_).getValueAt(2, ctr.getRow(), 0);
		Item item = (Item)((AssociationTable)group_).getValueAt(0, ctr.getRow(), 0);
		
		Forms forms = group_.getAncestorOfType(Forms.class);
		Customer customer = (Customer)forms.getField("customer").getValue();
		Vat appliedVat = SpringUtil.getBeanOfType(SalesManager.class).getAppliedVat(customer, item);
		
		BigDecimal vatRate = BigDecimal.ZERO;
		
		if(appliedVat != null){
			vatRate = appliedVat.getRate();
		}
		
		
		customer =(Customer)new GridController().activate(customer);
		BigDecimal discountRate = customer.getBestDiscountRate();
		
		
		BigDecimal discountedAmount = (price.multiply(quantity)).multiply((new BigDecimal(100).subtract(discountRate)).divide(new BigDecimal(100)));
		
		if(discountRate ==  BigDecimal.ZERO){
			discountedAmount = price.multiply(quantity);
		}
		BigDecimal bdiscount = (price.multiply(quantity)).subtract(discountedAmount);
		BigDecimal vat = (discountedAmount.multiply(vatRate)).divide(new BigDecimal(100));
		BigDecimal netAmount = discountedAmount.add(vat);
		
		
		
		BigDecimal totalDiscount = BigDecimal.ZERO;
		BigDecimal totalVat = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;

		((AssociationTable)group_).setValueAt(3,ctr.getRow(),0,bdiscount);
		((AssociationTable)group_).setValueAt(4,ctr.getRow(),0,vat);
		((AssociationTable)group_).setValueAt(5,ctr.getRow(),0,netAmount);
		
		
		
		
		AssociationTable table = (AssociationTable)group_;
		for(int i= 0;i < table.getRowCount();i++){
			BigDecimal discount = (BigDecimal)table.getValueAt(3, i, 0);
			BigDecimal vatAmount = (BigDecimal)table.getValueAt(4, i, 0);
			BigDecimal totalAmount = (BigDecimal)table.getValueAt(5, i, 0);
			
			if(discount != null){
				totalDiscount = totalDiscount.add(discount);
			}
			
			if(vatAmount != null){
				totalVat = totalVat.add(vatAmount);
			}
			
			if(totalAmount != null){
				total = total.add(totalAmount);
			}
			
		}
		
		InputControl input = container.getAncestorOfType(Forms.class).getField("total");
		input.setValue(total);
		InputControl uitotalVat = container.getAncestorOfType(Forms.class).getField("totalVat");
		uitotalVat.setValue(totalVat);
		
		InputControl uitotalDiscount = container.getAncestorOfType(Forms.class).getField("totalDiscount");
		uitotalDiscount.setValue(totalDiscount);
		
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
