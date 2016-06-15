package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"item", "quantity"})
public class AdjustmentLineItem extends BaseErpModel{
	
	@Column(caption="Product",style="width:70%")
	@Field(caption="Product",type=FieldType.lookup,lookupModel=Item.class, required=true)
	private Item item;
	
	@Column(caption="Quantity",style="width:30%")
	@Field(caption="Quantity",type=FieldType.Float,required=true)
	private BigDecimal quantity;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	

}
