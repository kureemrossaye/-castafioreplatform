package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.PriceFieldDecorator;
import org.castafiore.erp.ui.form.SelectProductDecorator;

@NodeEntity
@Table(columns={"item","quantity", "pricePerUnit","totalDiscount","totalVat", "total"})
public class PurchaseOrderLine extends BaseErpModel{
	
	@Field(caption="Price list",type=FieldType.lookup,lookupModel=PriceList.class)
	@Column(caption="Price list")
	private PriceList priceList;
	
	@Field(caption="Product",type=FieldType.lookup,lookupModel=Item.class, fieldInterceptor=SelectProductDecorator.class)
	@Column(caption="Product",style="width:55%")
	private Item item;
	
	@Field(caption="Vat",type=FieldType.lookup,lookupModel=Vat.class)
	@Column(caption="Vat")
	private Vat vat;
	
	@Field(caption="Quantity",type=FieldType.Float, fieldInterceptor=PriceFieldDecorator.class)
	@Column(caption="Quantity",style="width:15%")
	private BigDecimal quantity;
	
	@Field(caption="Price",type=FieldType.money)
	@Column(caption="Price", style="width:15%",editable=false)
	private BigDecimal pricePerUnit;
	
	@Field(caption="Total vat",type=FieldType.money)
	@Column(caption="Total vat")
	private BigDecimal totalVat;
	
	@Field(caption="Total discount",type=FieldType.money)
	@Column(caption="Total discount")
	private BigDecimal totalDiscount;
	
	@Field(caption="Total",type=FieldType.money)
	@Column(caption="Total", style="width:15%", formula="quantity*price", editable=false)
	private BigDecimal total;
	
	@Field(caption="Pre deliv date",type=FieldType.date)
	@Column(caption="Pre deliv date")
	private Date prevDeliveryDate;
	
	@Field(caption="Out quantity",type=FieldType.Float)
	@Column(caption="Out quantity")
	private BigDecimal outQuantity;
	
	@Field(caption="Inv. quantity",type=FieldType.Float)
	@Column(caption="Inv. quantity")
	private BigDecimal invoicedQuantity;
	
	private List<Discount> discounts = new ArrayList<Discount>();

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

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

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Date getPrevDeliveryDate() {
		return prevDeliveryDate;
	}

	public void setPrevDeliveryDate(Date prevDeliveryDate) {
		this.prevDeliveryDate = prevDeliveryDate;
	}

	public BigDecimal getOutQuantity() {
		return outQuantity;
	}

	public void setOutQuantity(BigDecimal outQuantity) {
		this.outQuantity = outQuantity;
	}

	public BigDecimal getInvoicedQuantity() {
		return invoicedQuantity;
	}

	public void setInvoicedQuantity(BigDecimal invoicedQuantity) {
		this.invoicedQuantity = invoicedQuantity;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}


}
