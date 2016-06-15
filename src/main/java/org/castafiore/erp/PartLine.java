package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.PriceFieldDecorator;
import org.castafiore.erp.ui.form.SelectProductDecorator;


@NodeEntity
@Table(columns={"item","quantity", "pricePerUnit", "total"})
public class PartLine extends BaseErpModel implements IAssociationModel{

	@Field(caption = "Product", type = FieldType.lookup, lookupModel = Item.class, fieldInterceptor = SelectProductDecorator.class)
	@Column(caption = "Product", style = "width:55%")
	private Item item;

	@Field(caption = "Vat", type = FieldType.lookup, lookupModel = Vat.class)
	@Column(caption = "Vat")
	private Vat vat;

	@Field(caption = "Quantity", type = FieldType.Float, fieldInterceptor = PriceFieldDecorator.class)
	@Column(caption = "Quantity", style = "width:15%")
	private BigDecimal quantity;

	@Field(caption = "Price", type = FieldType.Float)
	@Column(caption = "Price", style = "width:15%", editable = false)
	private BigDecimal pricePerUnit;

	@Field(caption = "Total vat", type = FieldType.Float)
	@Column(caption = "Total vat")
	private BigDecimal totalVat;

	@Field(caption = "Total", type = FieldType.Float)
	@Column(caption = "Total", style = "width:15%", formula = "quantity*price", editable = false)
	private BigDecimal total;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(BigDecimal totalVat) {
		this.totalVat = totalVat;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public Boolean isNull() {
		return item == null;
	}

}
