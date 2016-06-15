package org.castafiore.erp;


import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.audit.AuditTable;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * An account contains information that will be used in the accounting module.<br>
 * Accounts are configured in various entities in the system. e.g Customer, Vendors, Sales, Purchases, Charges.<br>
 * Actually it is configured in all entities that takes part in a financial transaction.<br>
 * These accounts help to properly make double entry in our ledger.<br>
 * The double entry is transparent to the user. <br>
 * Meaning for example, whenever there is a sales or purchase, the double entry is done automatically based on the configurations provided.<br>
 * <b>Note: <br>It is important that the configuration of accounts are done with the help of an accountant in order to properly maintain the different ledgers<b>
 * 
 * @author acer
 *
 */

@Table(columns = { "code", "description", "defaultColumn", "ledger" })
@Forms(name = "AccountForm", label = "Accounts", forms = {
		@Form(name = "Main", label = "Main", groups = { 
			@Group(name = "MainGroup", label = "Main", fields = {"code", "description", "defaultColumn", "ledger" }) 
		}),
		@Form(layout = "12", label = "History", name = "History", lazy = true, groups = { 
			@Group(layoutData = "0:0", label = "History", name = "History", impl = AuditTable.class) 
		}) 
	})
@DropDown(columns = { "code", "description", "defaultColumn" }, defaultSearch = {})
@NodeEntity
public class Account extends BaseErpModel {

	@Field(caption = "Code", required = true, validators = { "unique" }, actions = CodeFieldAction.class)
	@Column(caption = "Code", style = "width:10%")
	private String code;

	@Field(caption = "Description")
	@Column(caption = "Description", style = "width:50%")
	private String description;

	@Field(type = FieldType.select, caption = "Default column", items = {
			@Item(id = "debit", text = "Debit"),
			@Item(id = "credit", text = "Credit") })
	@Column(caption = "Default column", style = "width:20%")
	private String defaultColumn;

	@Field(caption = "Ledger", type = FieldType.lookup, lookupModel = Ledger.class, required = true)
	@Column(caption = "Ledger", style = "width:20%")
	private Ledger ledger;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultColumn() {
		return defaultColumn;
	}

	public void setDefaultColumn(String defaultColumn) {
		this.defaultColumn = defaultColumn;
	}

	public String toString() {
		return code + " - " + description;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}

}
