package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.math.BigInteger;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Forms(name="Payment",label="Payment",forms={
		@Form(name="Payment",label="Payment",layout="12;12;12",groups={
				@Group(name="G2",label="Advanced",layoutData="0:0", fields={ "trustAmount","daysCredit", "graceDays", }),
				@Group(name="G1",label="Payment",layoutData="0:1",fields={ "defaultPaymentType"}),
				@Group(name="G2",label="Fine charge", layoutData="0:2",fields={"fine"}),
				
		})
})
@Table(columns={"code", "title", "step", "firstInstallmentDays", "defaultPaymentType"})
public class PaymentMethod extends BaseErpModel{
	
	/**
	 * Indicates the maximum amount of credit that can be allowed to the customer.<br>
	 * It is possible to enforce validations in the system to reject any attempt to offer credit above the trust amount
	 */
	@Column(caption="Credit limit",size="20%")
	@Field(caption="Credit limit", type=FieldType.money,size=10,min=0)
	private BigDecimal trustAmount;
	
	/**
	 * Indicates the maximum of days that the customer is allowed.
	 */
	@Field(caption="Days credit", type=FieldType.Int,size=10,min=0)
	private BigInteger daysCredit = BigInteger.ZERO;
	
	
	
	
	/**
	 * The default bank to use when doing transaction with this customer.
	 * Transactions in a company are generally associated with a bank account. e.g when making payment for a credit sales<br>
	 * Or making refunds or whatever. This field conveniently help to automatically use the proper bank when selecting this customer.<br>
	 * It is possible to leave this blank. However each time a new transaction is performed, the user will have to implicitly specify the bank.
	 * 
	 */
	@Field(caption="Bank",type=FieldType.lookup, lookupModel=Bank.class)
	private Bank bank;
	
	/**
	 * This field is the default price list associated with this customer.<br>
	 * It is possible to configure many price list in the system e.g retail sales price list, whole sales price list etc etc.<br>
	 * Furthermore, it is possible to configure a price list for a customer so that when performing a sales for this customer, the prices for products
	 * are automatically retrieved from the specified price list.<br>
	 */
	@Field(caption="Price list",type=FieldType.lookup, lookupModel=PriceList.class)
	private PriceList priceList;
	
	/**
	 * This field represent the number of grace days <br>
	 * i.e. day allowed offered to client to start payment after charge or fine becomes applicable.
	 */
	@Field(caption="Days grace", type=FieldType.Int,size=10,min=0)
	private BigInteger graceDays;
	
	
	
	@Field(caption="Installments",type=FieldType.Int,size=5,maxLength=5,min=0,max=100,required=true)
	@Column(caption="Installments")
	private BigInteger numberOfInstallments;
	
	
	@Field(caption="Step",type=FieldType.Int,size=5,maxLength=5,min=1,max=100,required=true)
	@Column(caption="Step")
	private BigInteger step;
	
	@Field(caption="Cut off date",type=FieldType.Int,size=5,maxLength=5,min=1,max=31,required=true)
	@Column(caption="Cut off.")
	private BigInteger firstInstallmentDays;
	
	@Column(caption="Payment type")
	@Field(caption="Payment type",type=FieldType.lookup,lookupModel=PaymentType.class,required=true)
	private PaymentType defaultPaymentType;
	
	
	@Field(caption="Fine charge",type=FieldType.form,lookupModel=Fine.class)
	private Fine fine;
	

	

	public BigInteger getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(BigInteger numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	public BigInteger getStep() {
		return step;
	}

	public void setStep(BigInteger step) {
		this.step = step;
	}

	public BigInteger getFirstInstallmentDays() {
		return firstInstallmentDays;
	}

	
	public void setFirstInstallmentDays(BigInteger firstInstallmentDays) {
		this.firstInstallmentDays = firstInstallmentDays;
	}

	public PaymentType getDefaultPaymentType() {
		return defaultPaymentType;
	}

	public void setDefaultPaymentType(PaymentType defaultPaymentType) {
		this.defaultPaymentType = defaultPaymentType;
	}
	
	public Fine getFine() {
		return fine;
	}

	public void setFine(Fine fine) {
		this.fine = fine;
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

	public BigInteger getGraceDays() {
		return graceDays;
	}

	public void setGraceDays(BigInteger graceDays) {
		this.graceDays = graceDays;
	}
			
			
	

}
