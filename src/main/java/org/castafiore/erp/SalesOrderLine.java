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
@Table(columns={"type","item","otherDescription","quantity", "pricePerUnit","totalDiscount","totalVat", "total"})

public class SalesOrderLine extends BaseErpModel implements IAssociationModel{
	
	@Field(caption="Type",type=FieldType.lookup,lookupModel=SalesOrderLineType.class)
	@Column(caption="Type",style="width:10%")
	private SalesOrderLineType type;
	
	@Field(caption="Price list",type=FieldType.lookup,lookupModel=PriceList.class)
	@Column(caption="Price list")
	private PriceList priceList;
	
	@Field(caption="Item",type=FieldType.lookup,lookupModel=Item.class, fieldInterceptor=SelectProductDecorator.class,required=true)
	@Column(caption="Item",style="width:20%")
	private Item item;
	
	@Field(caption="Other desc.")
	@Column(caption="Other description",style="width:20%")
	private String otherDescription;
	
	@Field(caption="Vat",type=FieldType.lookup,lookupModel=Vat.class)
	@Column(caption="Vat")
	private Vat vat;
	
	/**
	 * Returns the quantity in Sales order
	 */
	@Field(caption="Quantity",type=FieldType.Float, fieldInterceptor=PriceFieldDecorator.class,required=true,min=1)
	@Column(caption="Quantity",style="width:8%")
	private BigDecimal quantity;
	
	@Field(caption="Unit price",type=FieldType.money,editable=false)
	@Column(caption="Unit price", style="width:8%",editable=false)
	private BigDecimal pricePerUnit;
	
	@Field(caption="Total vat",type=FieldType.money,editable=false)
	@Column(caption="Total vat", style="width:8%",editable=false)
	private BigDecimal totalVat;
	
	@Field(caption="Unit discount",type=FieldType.money,editable=false)
	@Column(caption="Unit discount", style="width:8%",editable=false)
	private BigDecimal totalDiscount;
	
	@Field(caption="Net amount",type=FieldType.money,editable=false)
	@Column(caption="Net amount", style="width:15%", formula="quantity*price", editable=false)
	private BigDecimal total;
	
	@Field(caption="Pre deliv date",type=FieldType.date)
	@Column(caption="Pre deliv date")
	private Date prevDeliveryDate;
	
	/**
	 * Represents the quantity delivered
	 */
	@Field(caption="Out quantity",type=FieldType.date)
	@Column(caption="Out quantity")
	private BigDecimal outQuantity;
	
	/**
	 * Represents the quantity already invoiced
	 */
	@Field(caption="Inv. quantity",type=FieldType.date)
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

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
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

	@Override
	public Boolean isNull() {
		return item == null;
	}

	public SalesOrderLineType getType() {
		return type;
	}

	public void setType(SalesOrderLineType type) {
		this.type = type;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
	
	
	

}
