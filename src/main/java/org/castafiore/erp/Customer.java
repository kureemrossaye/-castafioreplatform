package org.castafiore.erp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.erp.annotations.ActionScope;
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
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.neo4j.ogm.annotation.NodeEntity;



/**
 * Represents a Customer in the organization.<br>
 * Customers are used for sales.
 * @author acer
 *
 */
@NodeEntity
@Table(columns={"code", "companyName","vatNo","brn", "category"})

@Forms(label="CustomerForm",
		name="CustomerForm", 
		forms={
			@Form(layout="12;12",label="Main",name="Main",groups={
				@Group(name="G1",label="Main", layoutData="0:0",fields={"code","companyNumber", "category","companyName","idNumber","vatNo","brn","businessUnit", "type", "salesman", "currency", "vat"}),
				
				@Group(name="G3",label="Note",layoutData="0:1", fields={"note"})
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
			@Form(layout="12",label="Payment terms",name="Payment",groups={
					@Group(layoutData="0:0",label="Payment",name="Payment",fields={"paymentTerms"})
			}),
			@Form(layout="12",label="Attachements",name="Attachements" ,groups={
					@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
			}),
			@Form(layout="12",label="Comments",name="Comments" ,groups={
					@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
			})
				
		}
	)

public class Customer extends BaseAttachementableErpModel{
	
	
	
	/**
	 * A unique code to be assigned to the customer.<br>
	 * The code is generated automatically.<br>
	 * Once the code is generated, it cannot be modified or re-used again.
	 */
	@Column(caption="Code",size="20%")
	@Field(caption="Code", required=true,size=8,maxLength=8, validators={"unique"},updateable=false,actions=CodeFieldAction.class, actionScope=ActionScope.create)
	private String code;
	
	
	
	
	/**
	 * Addresses of the customer.
	 * It is possible to create many addresses for the customer.<br>
	 * However, when retrieving a single address for a customer, the system will first search
	 * for the address that is marked as default. If no Address found, then the first one in the list produced naturally from the database is used.
	 */
	@Field(caption="Addresses",type=FieldType.table,lookupModel= ContactAddress.class)
	private List<ContactAddress> addresses = new ArrayList<ContactAddress>();
	
	/**
	 * Is is possible to associate more than one contact to a customer.
	 */
	@Field(caption="Contacts",type=FieldType.table,lookupModel= Contact.class)
	private List<Contact> contacts = new ArrayList<Contact>();
	
	/**
	 * Vat no. of the customer. <br>
	 * The format should be VAT######## and 11 character long.
	 */
	@Field(caption="VAT no.",size=11,maxLength=11,format="VAT99999999",required=true)
	@Column(caption="VAT no.")
	private String vatNo;
	
	/**
	 * BRN number of the customer<br>
	 * The format should be C######## and 9 characters long
	 */
	@Field(caption="BRN.",size=9,maxLength=9,format="C99999999",required=true)
	@Column(caption="BRN.")
	private String brn;
	
	/**
	 * Company number of the customer. It should be a maximum of 15 characters long
	 */
	@Field(caption="File No.",size=15,maxLength=15, required=true)
	@Column(caption="File No.")
	private String companyNumber;
	
	/**
	 * Company name of customer
	 */
	@Field(caption="Name")
	@Column(caption="Number")
	private String companyName;
	
	@Field(caption="ID. Num",required=true,maxLength=14,size=14)
	private String idNumber;
	
	/**
	 * Notes
	 * Convenient field to add notes associated to this customer.<br>
	 * It should be noted that it is possible to add comments and attachments to any customer<br>
	 * This field can be used for very special note 
	 */
	@Field(caption="Note" ,style="width:100%;height:30px",type=FieldType.textArea)
	private String note;
	
	
	
	
	/**
	 * Default discount to be applied when doing transaction with this Customer
	 */
	@Field(caption="Discounts",type=FieldType.table,lookupModel= Discount.class)
	private List<Discount> discounts = new ArrayList<Discount>();
	
	
	
	/**
	 * This allows to categorize a customer to offer different type of features depending on the type
	 */
	@Field(caption="Type", type=FieldType.lookup,lookupModel=CustomerType.class,required=true)
	private CustomerType type;
	
	@Field(caption="Category", type=FieldType.lookup,lookupModel=CustomerCategory.class,required=true)
	@Column(caption="Category")
	private CustomerCategory category;
	
	@Field(caption="BusinessUnit", type=FieldType.lookup,lookupModel=BusinessUnit.class)
	private BusinessUnit businessUnit;
	
	
	@Field(caption="Currency", type=FieldType.lookup,lookupModel=Currency.class,required=true)
	private Currency currency;
	
	@Field(caption="Sales Man", type=FieldType.lookup,lookupModel=Employee.class,required=true)
	private Employee salesman;
	
	
	/**
	 * Default VAT to be used when doing transaction with this customer.<br>
	 * The value entered here will be populated automatically in tax field for sales orders<br>
	 * This is used for convenience where the user will not need to specify tax code for each customer<br>
	 * It is of course possible to change the tax code freely when creating a new sales order
	 */
	@Field(caption="VAT Code",type=FieldType.lookup, lookupModel=Vat.class,required=true)
	private Vat vat;
	
	
	
	@Field(caption="Payment terms",type=FieldType.form,lookupModel=PaymentMethod.class)
	private PaymentMethod paymentTerms;
	
	/**
	 * Accounts to be used when doing transactions with this Customer
	 */
	private List<AccountAssociations> accounts = new ArrayList<AccountAssociations>();
	
	
	public List<ContactAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<ContactAddress> addresses) {
		this.addresses = addresses;
	}


	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public List<Discount> getDiscounts() {
		return discounts;
	}
	
	public BigDecimal getBestDiscountRate(){
		long now = System.currentTimeMillis();
		double rate = 0.0d;
		for(Discount discount : discounts){
			Date from = discount.getStartDate();
			Date to = discount.getEndDate();
			
			long lFrom = Long.MIN_VALUE;
			long lTo = Long.MAX_VALUE;
			if(from != null){
				lFrom = from.getTime();
			}
			
			if(to != null){
				lTo = to.getTime();
			}
			
			
			if(lFrom <= now && lTo >= now){
				if(discount.getPercent().doubleValue() > rate){
					rate = discount.getPercent().doubleValue();
				}
			}
		}
		return new BigDecimal( (rate/100) + "");
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

	public String toString(){
		return code + " " + companyName;
	}

	

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	public PaymentMethod getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(PaymentMethod paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public CustomerCategory getCategory() {
		return category;
	}

	public void setCategory(CustomerCategory category) {
		this.category = category;
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public Employee getSalesman() {
		return salesman;
	}

	public void setSalesman(Employee salesman) {
		this.salesman = salesman;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	
}
