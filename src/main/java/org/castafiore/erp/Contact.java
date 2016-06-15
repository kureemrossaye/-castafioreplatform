package org.castafiore.erp;

import java.util.Date;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.CodeFieldAction;

@Table(columns={"code","firstName", "lastName"})
@Forms(name="Contact",label="Contact",forms={
		@Form(layout="12",name="Contact",label="Contact",groups={
				@Group(layoutData="0:0",label="Main",name="G1",fields={"code","title","firstName", "lastName","phone","mobile","email","fax"})
		})
})

//"code","firstName", "lastName","gender","maritalStatus","dateOfBirth"
//"birthPlace","phone","mobile","email","fax","socialSecurity"
@NodeEntity
public class Contact extends BaseErpModel{
	
	@Column(caption="Code")
	@Field(caption="Code", required=true,size=8,maxLength=8, validators={"unique"},updateable=false,actions=CodeFieldAction.class, actionScope=ActionScope.create)
	private String code;
	
	@Field(caption="Title",type=FieldType.select,items= {@Item(id="Mr", text="Mr"),@Item(id="Mrs", text="Mrs"),@Item(id="Miss", text="Miss")})
	private String title;
	
	
	@Column(caption="First name",size="30%")
	@Field(caption="First name", required=true,maxLength=80)
	private String firstName;
	
	@Column(caption="Last name",size="30%")
	@Field(caption="Last name", required=true,maxLength=80)
	private String lastName;
	
	/**
	 * Social security number
	 */
	@Field(caption="SS Number" ,size=10,maxLength=80)
	private String socialSecurity;
		
	@Field(caption="Phone",size=15,maxLength=15,format="+(999) 999-9999")
	private String phone;
	
	@Field(caption="Mobile",size=17,maxLength=17,format="+(999) 5-999-9999")
	private String mobile;
	
	@Field(caption="Email",size=10,maxLength=80,validators={"email"})
	private String email;
	
	@Field(caption="Fax" ,size=15,maxLength=15,format="+(999) 999-9999")
	private String fax;
	
	@Field(caption="Gender",type=FieldType.select, items={@Item(id = "M", text = "Male"),	@Item(id = "F", text = "Female") })
	private String gender;
	
	@Field(caption="Marital status" ,type=FieldType.select, items={@Item(id = "M", text = "Married"),	@Item(id = "S", text = "Single"),@Item(id = "D", text = "Divorced") })
	private String maritalStatus;
	
	@Field(caption="DOB", type=FieldType.date,size=10,maxLength=10)
	private Date dateOfBirth;
	
	@Field(caption="Birth place",size=10,maxLength=80)
	private String birthPlace;

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	
	

}
