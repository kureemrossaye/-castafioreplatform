package org.castafiore.erp;


import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.neo4j.ogm.annotation.NodeEntity;
/**
 * Represents a address with various input options.<br>
 * It is possible to add multiple address to a customer of vendor or employee.<br>
 * For convenience, there is a name that is added which can be used to identify different type of address. e.g billing address, delivery address.
 * 
 * @author acer
 *
 */
@Table(columns={"name","addressLine1", "addressLine2","city"})
@Search(defaultProperties={"name","addressLine1", "addressLine2"})
@DropDown(columns={"name","addressLine1", "addressLine2"},defaultSearch={})
@Forms(name="Address",label="Address",forms={
		@Form(layout="12",name="Address",label="Address",groups={
				@Group(layoutData="0:0",name="G1",label="Main",fields={"name","addressLine1", "addressLine2","city","zip", "country","defaultAddress"})
		})
})

@NodeEntity
public class ContactAddress extends BaseErpModel{
	
	/**
	 * Name of the address. <br>
	 * It is a convenient field to easily differentiate between different type of addresses. e.g billing address, delivery address
	 */
	@Field(caption="Name", required=true,maxLength=10,size=10,actions=CodeFieldAction.class,actionScope=ActionScope.create)
	@Column(caption="Name")
	private String name;
	
	/**
	 * First line of address
	 */
	@Field(caption="Address line 1", required=true,maxLength=80)
	@Column(caption="Address line 1")
	private String addressLine1;
	
	/**
	 * Second line of address
	 */
	@Field(caption="Address line 2" ,maxLength=80)
	@Column(caption="Address line 2")
	private String addressLine2;
	
	/**
	 * The city
	 */
	@Field(caption="City",maxLength=80)
	@Column(caption="City")
	private String city;
	
	/**
	 * The zip code
	 */
	@Field(caption="Zip",maxLength=80)
	private String zip;
	
	/**
	 * The province
	 */
	@Field(caption="Province",maxLength=80)
	private String province;
	
	/**
	 * The country
	 */
	@Field(caption="Country",maxLength=80)
	private String country;
	
	/**
	 * Indicates if this is a default address
	 */
	@Field(caption="Default",type=FieldType.Boolean)
	private Boolean defaultAddress = false;
	
	

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	
	
	
	

}
