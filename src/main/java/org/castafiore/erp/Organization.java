package org.castafiore.erp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Table(columns = { "code", "companyName", "brn", "vatNo" ,"phone","fax", "email"})
@Forms(name = "Organization", label = "Organization", forms = {
		@Form(name = "FO1", label = "Contact Info", layout = "12;12;12;12;12", groups = {
				@Group(name = "G1", layoutData = "0:0", label = "Contact Info", fields = {"code", "companyName", "slogan","phone", "mobile","fax", "email","website" }),
				@Group(name = "G2", layoutData = "0:1", label = "Address", fields = { "address" }),
				@Group(name = "G3", layoutData = "0:2", label = "Legal Info", fields = {"legalName", "legalAddress", "legalCountry" }),
				@Group(name = "G4", layoutData = "0:3", label = "Report Info", fields = {"startFinancialYear", "startTaxYear" })

		}),
		@Form(layout = "12;12", label = "Financial Info", name = "FinancialInfo", groups = {
				@Group(name = "G1", layoutData = "0:0", label = "Company Indentification", fields = {"vatNo", "brn" }),
				@Group(name = "G2", layoutData = "0:0", label = "Payroll Information", fields = {"employersPayeRefNumber", "employersContractingOutNo",	"taxOfficeName", "accountsOfficeRef" }), 
		},lazy=true),
		@Form(layout = "12", label = "Ship to Address", name = "ShipTo", groups = { 
				@Group(name = "G2", layoutData = "0:0", label = "Address", fields = { "shipToAddress" }), 
		},lazy=true),
		@Form(layout = "12", label = "Attachements", name = "Attachements", groups = { 
				@Group(layoutData = "0:0", label = "Attachements", name = "Attachements", impl = Attachements.class) 
		},lazy=true),
		@Form(layout = "12", label = "Comments", name = "Comments", groups = { 
				@Group(layoutData = "0:0", label = "Comments", name = "Comments", impl = CommentsTable.class) 
		},lazy=true) 
		})
public class Organization extends BaseAttachementableErpModel {

	@Field(caption = "Code", required = true, maxLength = 8, validators = { "unique" }, actions = CodeFieldAction.class)
	@Column(caption = "Code")
	private String code;

	@Field(caption = "Vat no.", size = 11, maxLength = 11, format = "VAT99999999")
	@Column(caption = "Vat no.")
	private String vatNo;

	@Field(caption = "BRN.", size = 9, maxLength = 9, format = "C99999999")
	@Column(caption = "BRN.")
	private String brn;

	@Field(caption = "Company name", required = true)
	@Column(caption = "Company name")
	private String companyName;
	
	@Field(caption = "Slogan")
	@Column(caption = "Slogan")
	private String slogan;

	@Field(caption = "Address", type = FieldType.form,lookupModel=ContactAddress.class)
	private ContactAddress address;

	@Field(caption = "Legal Name")
	@Column(caption = "Legal Name")
	private String legalName;

	@Field(caption = "Legal Address", type = FieldType.form,lookupModel=ContactAddress.class)
	private ContactAddress legalAddress;

	@Field(caption = "Legal Country")
	@Column(caption = "Legal Country")
	private String legalCountry;

	@Field(caption = "Start financial Year", type = FieldType.select, items = {
			@Item(id = "Jan", text = "January"),
			@Item(id = "Feb", text = "February"),
			@Item(id = "Mar", text = "March"),
			@Item(id = "Apr", text = "April"),
			@Item(id = "May", text = "May"),
			@Item(id = "Jun", text = "June"),
			@Item(id = "Jul", text = "July"),
			@Item(id = "Aug", text = "August"),
			@Item(id = "Sep", text = "September"),
			@Item(id = "Oct", text = "October"),
			@Item(id = "Nov", text = "November"),
			@Item(id = "Dec", text = "December"),
			})
	private String startFinancialYear;

	@Field(caption = "Start tax Year", type = FieldType.select, items = {
			@Item(id = "Jan", text = "January"),
			@Item(id = "Feb", text = "February"),
			@Item(id = "Mar", text = "March"),
			@Item(id = "Apr", text = "April"),
			@Item(id = "May", text = "May"),
			@Item(id = "Jun", text = "June"),
			@Item(id = "Jul", text = "July"),
			@Item(id = "Aug", text = "August"),
			@Item(id = "Sep", text = "September"),
			@Item(id = "Oct", text = "October"),
			@Item(id = "Nov", text = "November"),
			@Item(id = "Dec", text = "December"),
			})
	private String startTaxYear;

	@Field(caption="Phone",size=15,maxLength=15,format="+(999) 999-9999")
	@Column(caption = "Phone")
	private String phone;
	
	@Field(caption="Mobile",size=17,maxLength=17,format="+(999) 5-999-9999")
	private String mobile;

	@Field(caption="Fax",size=15,maxLength=15,format="+(999) 999-9999")
	@Column(caption = "Fax")
	private String fax;

	@Field(caption="Email",size=10,maxLength=80,validators={"email"})
	@Column(caption = "Email")
	private String email;

	@Field(caption="Website")
	private String website;

	@Field(caption = "Ship to Address", type = FieldType.form,lookupModel=ContactAddress.class)
	private ContactAddress shipToAddress;

	@Field(caption = "Business No.", size = 9, maxLength = 9, format = "C99999999")
	private String businessNumber;

	@Field(caption = "PAYE ref No.")
	private String employersPayeRefNumber;

	@Field(caption = "Contracting out No.")
	private String employersContractingOutNo;

	@Field(caption = "Tax office name")
	private String taxOfficeName;

	@Field(caption = "Account office ref.")
	private String accountsOfficeRef;

	@Field(caption = "Contacts", type = FieldType.table, lookupModel = Contact.class)
	private List<Contact> contacts = new ArrayList<Contact>();

	private String language = Locale.getDefault().getLanguage();

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public ContactAddress getAddress() {
		return address;
	}

	public void setAddress(ContactAddress address) {
		this.address = address;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public ContactAddress getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(ContactAddress legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getStartFinancialYear() {
		return startFinancialYear;
	}

	public void setStartFinancialYear(String startFinancialYear) {
		this.startFinancialYear = startFinancialYear;
	}

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public ContactAddress getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(ContactAddress shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getEmployersPayeRefNumber() {
		return employersPayeRefNumber;
	}

	public void setEmployersPayeRefNumber(String employersPayeRefNumber) {
		this.employersPayeRefNumber = employersPayeRefNumber;
	}

	public String getEmployersContractingOutNo() {
		return employersContractingOutNo;
	}

	public void setEmployersContractingOutNo(String employersContractingOutNo) {
		this.employersContractingOutNo = employersContractingOutNo;
	}

	public String getTaxOfficeName() {
		return taxOfficeName;
	}

	public void setTaxOfficeName(String taxOfficeName) {
		this.taxOfficeName = taxOfficeName;
	}

	public String getAccountsOfficeRef() {
		return accountsOfficeRef;
	}

	public void setAccountsOfficeRef(String accountsOfficeRef) {
		this.accountsOfficeRef = accountsOfficeRef;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	

	public String getLegalCountry() {
		return legalCountry;
	}

	public void setLegalCountry(String legalCountry) {
		this.legalCountry = legalCountry;
	}

	public String getStartTaxYear() {
		return startTaxYear;
	}

	public void setStartTaxYear(String startTaxYear) {
		this.startTaxYear = startTaxYear;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	

}
