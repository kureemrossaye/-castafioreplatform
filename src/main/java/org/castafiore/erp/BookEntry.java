package org.castafiore.erp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.audit.AuditTable;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Forms(name="Book entry",label="Book entry",forms={
		@Form(name="BookEntry",label="BookEntry",layout="12;12", groups={
				@Group(name="G1",label="Main",fields={"dateOfTransaction", "motive", "description"}),
				@Group(name="G1", label="Lines",fields={"lines"},displayType=DisplayType.TABLE)
		}),
		@Form(layout="12",label="Versions",name="Versions" ,lazy=true,groups={
				@Group(layoutData="0:0",label="Versions",name="Versions" ,impl=AuditTable.class)
		}),
		@Form(layout="12",label="Attachements",name="Attachements" ,groups={
				@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
		}),
		@Form(layout="12",label="Comments",name="Comments" ,groups={
				@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
		})
})
@Table(columns={"dateOfTransaction", "motive", "totalDebit", "totalCredit"})
@DropDown(columns={"dateOfTransaction", "motive", "totalDebit", "totalCredit"},defaultSearch={})

public class BookEntry extends BaseAttachementableErpModel{
	
	@Field(caption="Date",required=true,type=FieldType.date)
	@Column(caption="Date")
	private Date dateOfTransaction;
	
	@Field(caption="Motive",required=true,type=FieldType.lookup,lookupModel=AccountingMotive.class)
	@Column(caption="Motive")
	private AccountingMotive motive;
	
	@Field(caption="Note",type=FieldType.textArea, style="height:50px")
	@Column(caption="Note")
	private String description;
	
	@Field(caption="Debit",type=FieldType.money,editable=false)
	@Column(caption="Debit")
	private BigDecimal totalDebit = BigDecimal.ZERO;
	
	@Field(caption="Credit",type=FieldType.money,editable=false)
	@Column(caption="Credit")
	private BigDecimal totalCredit=BigDecimal.ZERO;
	
	@Field(caption="Lines",type=FieldType.table,lookupModel=BookEntryLine.class)
	private List<BookEntryLine> lines = new ArrayList<BookEntryLine>();

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public AccountingMotive getMotive() {
		return motive;
	}

	public void setMotive(AccountingMotive motive) {
		this.motive = motive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BookEntryLine> getLines() {
		return lines;
	}

	public void setLines(List<BookEntryLine> lines) {
		this.lines = lines;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}
	
	
	
	
	

}
