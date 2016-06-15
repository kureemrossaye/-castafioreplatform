package org.castafiore.erp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Table(columns={"code","companyNumber", "companyName","vatNo","brn"})

@Forms(label="SupplierForm",
		name="SupplierForm", 
		forms={
			@Form(layout="6:6;12;12",label="Main",name="Main",groups={
				@Group(name="G1",label="Main", layoutData="0:0",fields={"code","companyNumber", "companyName","type", "category","currency"}),
				@Group(name="G2",label="Advanced",layoutData="1:0", fields={"brn","vatNo","vat","bank","priceList","trustAmount","daysCredit"}),
				@Group(name="G3",label="Note",layoutData="0:2", fields={"note"})
			}),
			@Form(layout="12",label="Contacts", name="Contacts", groups={
					@Group(name="G2",label="Contact",layoutData="0:0",fields={"contacts"},displayType=DisplayType.TABLE_FORM),
			}),
			@Form(layout="12",label="Addresses", name="Addresses", groups={
					@Group(name="G4",label="Addresses",layoutData="0:0", fields={"addresses"},displayType=DisplayType.TABLE_FORM),
			}),
			@Form(layout="12",label="Discounts", name="Discounts", groups={
					@Group(name="G1",label="Discounts",layoutData="0:0",fields={"discounts"}, displayType=DisplayType.TABLE)
			}),
			@Form(layout="12",label="Attachements",name="Attachements" ,groups={
					@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
			}),
			@Form(layout="12",label="Comments",name="Comments" ,groups={
					@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
			})
				
		}
	)

public class Supplier extends BaseAttachementableErpModel{
	
	@Column(caption="Code",size="20%")
	@Field(caption="Code", required=true,size=8,maxLength=80)
	private String code;
	
	@Column(caption="Credit limit",size="20%")
	@Field(caption="Credit limit", type=FieldType.money,size=10,maxLength=80)
	private BigDecimal trustAmount;
	
	@Field(caption="Days credit", type=FieldType.Int,size=10,maxLength=80)
	private BigInteger daysCredit = BigInteger.ZERO;
	
	
	@Field(caption="Addresses",type=FieldType.table,lookupModel= ContactAddress.class)
	private List<ContactAddress> addresses = new ArrayList<ContactAddress>();
	
	@Field(caption="Contacts",type=FieldType.table,lookupModel= Contact.class)
	private List<Contact> contacts = new ArrayList<Contact>();
	
	
	@Field(caption="VAT no.",size=15,maxLength=80)
	@Column(caption="Vat no.")
	private String vatNo;
	
	@Field(caption="BRN.",size=15,maxLength=80)
	@Column(caption="BRN.")
	private String brn;
	
	@Field(caption="Company number",size=15,maxLength=80)
	@Column(caption="Company number")
	private String companyNumber;
	
	@Field(caption="Company name")
	@Column(caption="Company number")
	private String companyName;
	
	
	@Field(caption="Note" ,style="width:100%;height:30px",type=FieldType.textArea)
	private String note;
	
	
	@Field(caption="VAT code",type=FieldType.lookup, lookupModel=Vat.class)
	private Vat vat;
	
	@Field(caption="Bank",type=FieldType.lookup, lookupModel=Bank.class)
	private Bank bank;
	
	@Field(caption="Price list",type=FieldType.lookup, lookupModel=PriceList.class)
	private PriceList priceList;
	
	@Field(caption="Discounts",type=FieldType.table,lookupModel= Discount.class)
	private List<Discount> discounts = new ArrayList<Discount>();
	
	private List<AccountAssociations> accounts = new ArrayList<AccountAssociations>();
	

	@Field(caption="Currency", type=FieldType.lookup,lookupModel=Currency.class)
	private Currency currency;
	
	private List<Activity> activities = new ArrayList<Activity>();
	
	
	/**
	 * This allows to categorize a Supplier to offer different type of features depending on the type
	 */
	@Field(caption="Type", type=FieldType.lookup,lookupModel=SupplierType.class,required=true)
	private SupplierType type;
	
	@Field(caption="Category", type=FieldType.lookup,lookupModel=SupplierCategory.class,required=true)
	@Column(caption="Category")
	private SupplierCategory category;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getTrustAmount() {
		return trustAmount;
	}

	public void setTrustAmount(BigDecimal trustAmount) {
		this.trustAmount = trustAmount;
	}

	public BigInteger getDaysCredit() {
		return daysCredit;
	}

	public void setDaysCredit(BigInteger daysCredit) {
		this.daysCredit = daysCredit;
	}

	public List<ContactAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<ContactAddress> addresses) {
		this.addresses = addresses;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getBrn() {
		return brn;
	}

	public void setBrn(String brn) {
		this.brn = brn;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public List<AccountAssociations> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountAssociations> accounts) {
		this.accounts = accounts;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public SupplierType getType() {
		return type;
	}

	public void setType(SupplierType type) {
		this.type = type;
	}

	public SupplierCategory getCategory() {
		return category;
	}

	public void setCategory(SupplierCategory category) {
		this.category = category;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	
	public String toString(){
		return this.code + " - " + this.companyName;
	}

}
