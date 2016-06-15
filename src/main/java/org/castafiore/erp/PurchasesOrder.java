package org.castafiore.erp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.Evaluator;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.annotations.Workflow;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.audit.AuditTable;
import org.castafiore.erp.ui.form.RefHistoryAction;
import org.castafiore.erp.ui.form.SupplierFieldDecorator;
import org.castafiore.erp.ui.sales.ShippingInformation;
import org.castafiore.erp.ui.workflow.WorkflowHistoryTable;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity

@Table(columns={"date", "referenceNumber", "supplier", "totalVat", "total"})
@Forms(name="SaesOrder",label="Purchases Order",forms={
		@Form(layout="8:4;12;8:4",label="Main",name="SalesOrder",groups={
				@Group(layoutData="0:0",label="Main",name="G1",fields={"referenceNumber","supplier","currency","agent","priceList"}),
				@Group(layoutData="1:0",label="...",name="G3",fields={"date", "commission","warehouse"}),
				@Group(layoutData="0:1",label="lines",name="lines",displayType=DisplayType.TABLE,fields={"lines"}),
				@Group(layoutData="1:2",label="...",name="G4",fields={"totalDiscount", "totalVat","total"}),
				
		}),
		@Form(layout="12;12", label="Addresses",name="Addresses", groups={
			@Group(layoutData="0:0",label="Billing Information",fields="billingAddress",name="Billing",showTitle=true),
			@Group(layoutData="0:1",label="Shipping Information",fields="shippingAddress",name="Shipping",showTitle=true,impl=ShippingInformation.class)
		}),
		@Form(layout="12",label="Attachements",name="Attachements" ,groups={
				@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
		}),
		@Form(layout="12",label="Workflow history",name="Workflow" ,groups={
				@Group(layoutData="0:0",label="Workflow history",name="Workflow history" ,impl=WorkflowHistoryTable.class)
		}),
		@Form(layout="12",label="Versions",name="Versions" ,lazy=true,groups={
				@Group(layoutData="0:0",label="History",name="History" ,impl=AuditTable.class)
		}),
		@Form(layout="12",label="Comments",name="Comments" ,groups={
				@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
		})
		
})
@Workflow
public class PurchasesOrder extends BaseAttachementableErpModel implements
		Vatable, Referentiable {

	public static BigInteger STATUS_PURCHASE_ORDER = new BigInteger("2");

	public static BigInteger STATUS_QUOTATIONS = new BigInteger("1");

	public static BigInteger STATUS_PURCHASE_INVOICE = new BigInteger("3");

	public static BigInteger STATUS_PURCHASE_RETURN = new BigInteger("4");

	@Field(caption = "Date", type = FieldType.date, required = true)
	@Column(caption = "Date", style = "width:10%")
	private Date date;

	@Field(caption = "Ref no.", required = true, validators = { "unique" }, updateable = false, actions = RefHistoryAction.class, actionScope = ActionScope.all)
	@Column(caption = "Ref No.", style = "width:10%")
	private String referenceNumber;

	@Field(caption = "Pricing", type = FieldType.lookup, lookupModel = PriceList.class, required = true)
	private PriceList priceList;

	@Field(caption = "Vat", type = FieldType.lookup, lookupModel = Vat.class, required = true)
	private Vat vat;

	@Field(caption = "Outlet", type = FieldType.lookup, lookupModel = Warehouse.class, required = true)
	private Warehouse warehouse;

	@Field(caption = "Allowance", type = FieldType.Float, style = "text-align:right")
	private BigDecimal allowance;

	@Field(caption = "Deposit", type = FieldType.Float, style = "text-align:right")
	private BigDecimal deposit;

	@Field(caption = "Taxable base", type = FieldType.Float, style = "text-align:right")
	private BigDecimal taxableBase;

	@Field(caption = "Total discount", type = FieldType.money, style = "text-align:right")
	private BigDecimal totalDiscount;

	@Field(caption = "Total vat", type = FieldType.money, style = "text-align:right")
	@Column(caption = "Total vat", style = "width:5%")
	private BigDecimal totalVat;

	@Field(caption = "Total", type = FieldType.money, style = "text-align:right")
	@Column(caption = "Total", style = "width:5%")
	@Evaluator(listen = { "lines.quantity", "priceList" })
	private BigDecimal total;

	@Field(caption = "Sales rep.", type = FieldType.lookup, lookupModel = Employee.class)
	private Employee agent;

	@Field(caption = "Commission", type = FieldType.Float)
	private BigDecimal commission;

	@Field(caption = "Note", type = FieldType.textArea)
	private String note;

	@Field(caption = "Header Note", type = FieldType.textArea)
	private String headerNote;

	@Field(caption = "Footer Note", type = FieldType.textArea)
	private String footerNote;

	@Field(caption = "Delivery Note", type = FieldType.textArea)
	private String deliveryNote;

	@Field(caption = "Discounts", type = FieldType.table, lookupModel = Discount.class)
	private List<Discount> discounts = new ArrayList<Discount>();

	@Field(caption = "Charges", type = FieldType.table, lookupModel = Charge.class)
	private List<Charge> charges = new ArrayList<Charge>();

	@Field(caption = "Activities", type = FieldType.table, lookupModel = SaleActivity.class)
	private List<SaleActivity> activities = new ArrayList<SaleActivity>();

	@Field(caption = "Billing Address", type = FieldType.form, lookupModel = ContactAddress.class)
	private ContactAddress billingAddress;

	@Field(caption = "Shipping Address", type = FieldType.form, lookupModel = ContactAddress.class)
	private ContactAddress shippingAddress;

	@Field(caption = "Currency", type = FieldType.lookup, lookupModel = Currency.class)
	private Currency currency;
	// //

	@Column(caption = "Supplier")
	@Field(caption = "Supplier", type = FieldType.lookup, lookupModel = Supplier.class, fieldInterceptor = SupplierFieldDecorator.class)
	private Supplier supplier;

	@Field(caption = "Lines", type = FieldType.table, lookupModel = PurchaseOrderLine.class)
	private List<PurchaseOrderLine> lines = new ArrayList<PurchaseOrderLine>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public BigDecimal getAllowance() {
		return allowance;
	}

	public void setAllowance(BigDecimal allowance) {
		this.allowance = allowance;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getTaxableBase() {
		return taxableBase;
	}

	public void setTaxableBase(BigDecimal taxableBase) {
		this.taxableBase = taxableBase;
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

	public Employee getAgent() {
		return agent;
	}

	public void setAgent(Employee agent) {
		this.agent = agent;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHeaderNote() {
		return headerNote;
	}

	public void setHeaderNote(String headerNote) {
		this.headerNote = headerNote;
	}

	public String getFooterNote() {
		return footerNote;
	}

	public void setFooterNote(String footerNote) {
		this.footerNote = footerNote;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public List<PurchaseOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<PurchaseOrderLine> lines) {
		this.lines = lines;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	@Override
	public String getDocumentType() {
		return "Purchases";
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public List<SaleActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<SaleActivity> activities) {
		this.activities = activities;
	}

	public ContactAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(ContactAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public ContactAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ContactAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	

}
