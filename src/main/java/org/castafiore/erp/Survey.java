package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.SurveyFieldInterceptor;

@NodeEntity
@Search(defaultProperties={"name", "fsCode", "plan","phone"})
@Table(columns={"fsCode","name", "plan","amount", "phone", "question1", "question2", "question3", "question4"})
@Forms(label="Survey",name="Survey",forms={
		@Form(layout="12",label="Survey",name="Survey",groups={ 
			@Group(fields={"fsCode","name", "plan","amount", "phone", "question1", "question2", "question3", "question4"},label="Survey",name="Survey",layoutData="0:0")
		})
})
public class Survey extends BaseErpModel{
	
	@Column(caption="Nom")
	@Field(caption="Nom",size=80)
	private String name;
	
	@Column(caption="Plan")
	@Field(caption="Plan",size=80)
	private String plan;
	
	@Column(caption="FS Code")
	@Field(caption="FS Code",size=80, type=FieldType.contract, fieldInterceptor=SurveyFieldInterceptor.class)
	private String fsCode;
	
	@Column(caption="Montant")
	@Field(caption="Montant",type=FieldType.Float)
	private BigDecimal amount;
	
	
	
	@Field(caption="Phone", size=15)
	@Column(caption="Phone", size="10%")
	private String phone;
	
	@Field(caption="Comment jugez vous le service que vois avez eu:",type=FieldType.select,items={@Item(id="", text=""),@Item(id="Moyen", text="Moyen"),@Item(id="Bien", text="Bien"),@Item(id="Tres Bien", text="Tres bien"),@Item(id="Mauvais", text="Mauvais"),@Item(id="Autres", text="Autres")})
	@Column(caption="Comment jugez vous le service..")
	private String question1;
	
	@Field(caption="Comment evaluez vous l'avantage d'etre un membre du funeral scheme?",type=FieldType.select,items={@Item(id="", text=""),@Item(id="Financier", text="Financier"),@Item(id="Promptitude", text="Promptitude"),@Item(id="Support", text="Support"),@Item(id="Service Assure", text="Service Assure"),@Item(id="Autres", text="Autres")})
	@Column(caption="Comment evaluez vous l'avantage..")
	private String question2;
	
	@Field(caption="Etes vous membre du funeral scheme",type=FieldType.select,items={@Item(id="", text=""),@Item(id="Oui", text="Oui"),@Item(id="Non", text="Non")})
	@Column(caption="Etes vous membre")
	private String question3;
	
	@Field(caption="Connaissez vous des parents ou amis qui seraient interesse a etre membres du funeral scheme?",type=FieldType.textArea)
	@Column(caption="Connaissances")
	private String question4;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(String question4) {
		this.question4 = question4;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
}
