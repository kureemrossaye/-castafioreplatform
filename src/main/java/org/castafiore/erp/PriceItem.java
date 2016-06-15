package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"item", "price"})
public class PriceItem extends BaseErpModel implements IAssociationModel{
	
	@Column(caption="Product", style="width:75%")
	@Field(caption="Product", type=FieldType.lookup, lookupModel=Item.class)
	private Item item;
	
	@Column(caption="Price",style="width:25%")
	@Field(caption="Price",type=FieldType.Float,required=true)
	private BigDecimal price;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public Boolean isNull() {
		if(item == null || price == null){
			return true;
		}else{
			return false;
		}
	}
	
	

}
