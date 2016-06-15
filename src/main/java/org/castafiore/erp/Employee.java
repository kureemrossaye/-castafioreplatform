package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;

@NodeEntity
@Table(columns={"firstName", "lastName", "phone", "mobile","email"})
@Forms(name="Employee",label="Employee",forms={
		@Form(name="Main",label="Main",layout="6:6;6:6",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"username","firstName", "lastName", "phone", "mobile", "email","socialSecurityNumber"}),
				@Group(layoutData="0:1",name="Address",label="Address",fields={"addressLine1", "addressLine2", "city", "zip","province","country"}),
				@Group(layoutData="1:0",name="Ext",label="Ext",fields={ "gender", "maritalStatus", "nidCardNumber","nationality","birthPlace","dateOfBirth"}),
				@Group(layoutData="1:1",name="Ext1",label="Ext 1",fields={"division", "office","hireDate","dismissedDate","level","salary","manager","assistant"}),
		}),
		@Form(layout="12",label="Attachements",name="Attachements" ,groups={
				@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
		}),
		@Form(layout="12",label="Comments",name="Comments" ,groups={
				@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
		})
		
})
@DropDown(columns={"username", "firstName", "lastName", "phone", "mobile","email"},defaultSearch={})
public class Employee extends BaseAttachementableErpModel{
	
	@Field(caption="Username",required=true,validators={"unique"})
	@Column(caption="Username")
	private String username;
	
	@Field(caption="Password",required=true,type=FieldType.password)
	private String password;
	
	@Field(caption="First Name",required=true)
	@Column(caption="First Name")
	private String firstName;
	
	@Field(caption="Last name",required=true)
	@Column(caption="Last name")
	private String lastName;
	
	@Field(caption="Address Line 1",required=true)
	@Column()
	private String addressLine1;
	
	@Field(caption="Address Line 2",required=true)
	@Column()
	private String addressLine2;
	
	@Field(caption="City")
	@Column()
	private String city;
	
	@Field(caption="Zip")
	@Column()
	private String zip;
	
	@Field(caption="Province")
	@Column()
	private String province;
	
	@Field(caption="Country")
	@Column()
	private String country;
	
	@Field(caption="SS Number")
	@Column()
	private String socialSecurityNumber;
	
	@Field(caption="Phone")
	@Column(caption="Phone")
	private String phone;
	
	@Field(caption="Mobile")
	@Column(caption="Mobile")
	private String mobile;
	
	@Field(caption="Gender",type=FieldType.select,items={@Item(id="Male", text="Male"),@Item(id="Female", text="Female"),@Item(id="Samuel", text="Samuel")} )
	@Column()
	private String gender;
	
	@Field(caption="Marital status", type=FieldType.select,items={@Item(id="Single", text="single"),@Item(id="Married", text="Married"),@Item(id="Divorced", text="Divorced")})
	@Column()
	private String maritalStatus;
	
	@Field(caption="NID no.")
	@Column()
	private String nidCardNumber;
	
	@Field(caption="Nationality")
	@Column()
	private String nationality;
	
	@Field(caption="Birth place")
	@Column()
	private String birthPlace;
	
	@Field(caption="DOB",type=FieldType.date)
	@Column()
	private Date dateOfBirth;
	
	@Field(caption="Division")
	@Column()
	private String division;
	
	@Field(caption="Office")
	@Column()
	private String office;
	
	@Field(caption="Email")
	@Column()
	private String email;
	
	@Field(caption="Hire date",type=FieldType.date)
	@Column()
	private Date hireDate;
	
	@Field(caption="Dismissed date",type=FieldType.date)
	@Column()
	private Date dismissedDate;
	
	@Field(caption="Level")
	@Column()
	private String level;
	
	@Field(caption="Salary",type=FieldType.Float)
	@Column()
	private BigDecimal salary;
	
	@Field(caption="Manager",type=FieldType.lookup, lookupModel=Employee.class)
	@Column()
	private Employee manager;
	
	@Field(caption="Assistant",type=FieldType.lookup, lookupModel=Employee.class)
	@Column()
	private Employee assistant;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNidCardNumber() {
		return nidCardNumber;
	}

	public void setNidCardNumber(String nidCardNumber) {
		this.nidCardNumber = nidCardNumber;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getDismissedDate() {
		return dismissedDate;
	}

	public void setDismissedDate(Date dismissedDate) {
		this.dismissedDate = dismissedDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Employee getAssistant() {
		return assistant;
	}

	public void setAssistant(Employee assistant) {
		this.assistant = assistant;
	}
	
	public String toString(){
		return firstName + " " + lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
