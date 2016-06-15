package org.castafiore.erp;


import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"contactVat", "lineItemVat", "contactHasPriority"})
@Forms(name="VatMatrix",label="Vat Matrix",forms={
		@Form(name="VatMatrix",label="Vat Matrix",layout="12",groups={
				@Group(layoutData="0:0",name="Matrix",label="Matrix entry",fields={"contactVat", "lineItemVat", "contactHasPriority"})
		})
})
public class VatMatrix extends BaseErpModel{
	
	/**
	 * This can be a supplier or a customer
	 */
	@Field(caption="Contact Vat Code",type=FieldType.lookup,lookupModel=Vat.class,required=true)
	@Column(caption="Contact Vat Code",style="width:40%")
	private Vat contactVat;
	
	
	/**
	 * This can be a product or a service
	 */
	@Field(caption="Line item Vat Code",type=FieldType.lookup,lookupModel=Vat.class,required=true)
	@Column(caption="Line item Vat Code",style="width:40%")
	private Vat lineItemVat;
	
	
	/**
	 * if true, contact has priority.<br>
	 * if false, line item has priority
	 */
	@Field(caption="Contact has priority",type=FieldType.Boolean,required=true)
	@Column(caption="Contact has priority",style="width:20%")
	private Boolean contactHasPriority = true;


	public Vat getContactVat() {
		return contactVat;
	}


	public void setContactVat(Vat contactVat) {
		this.contactVat = contactVat;
	}


	public Vat getLineItemVat() {
		return lineItemVat;
	}


	public void setLineItemVat(Vat lineItemVat) {
		this.lineItemVat = lineItemVat;
	}


	public Boolean getContactHasPriority() {
		return contactHasPriority;
	}


	public void setContactHasPriority(Boolean contactHasPriority) {
		this.contactHasPriority = contactHasPriority;
	}
	
	
	
	

}
