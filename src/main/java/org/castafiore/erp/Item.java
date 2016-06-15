package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.ArrayList;
import java.util.List;


import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.inventory.ItemInfoGroup;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.form.CodeFieldAction;

@NodeEntity
@Table(columns = { "code", "description", "type", "group" })
@Forms(label = "Product", name = "Product", forms = {
		@Form(layout = "12;12", label = "Product", name = "Product", groups = { 
				@Group(layoutData = "0:0", label = "Main", name = "Main", fields = {"code", "type", "category", "group", "manufactured","description", "shelfNo", "barcode", "baseMeasureUnit",	"sellingMeasureUnit" }),
				@Group(layoutData = "0:1", label = "Stock Info", name = "Stock Info", impl = ItemInfoGroup.class) 
		}),

		@Form(layout = "12;12;12", label = "Financials", name = "Financials", groups = {
				@Group(layoutData = "0:0", label = "Financials", name = "Financials", fields = {"vat", "wholesaleSellingPrice", "retailSellingPrice","costingMethod" }),
				@Group(layoutData = "0:1", label = "Accounts", name = "Accounts", fields = {"costOfSalesAC", "stockAC", "salesAC", "vatControlAC" })
		}),

		@Form(layout = "12;12", label = "Other info", name = "Other info", groups = { 
				@Group(layoutData = "0:0", label = "Other info", name = "Other info", fields = {"discontinued", "reorderLevel", "minReorderQty","prefferedSupplier", "prefferedSupplier", "allowChangePrice" }) 
		}),

		@Form(layout = "12", label = "Attachements", name = "Attachements", groups = { 
				@Group(layoutData = "0:0", label = "Attachements", name = "Attachements", impl = Attachements.class) 
		}),
		@Form(layout = "12", label = "Comments", name = "Comments", groups = { 
				@Group(layoutData = "0:0", label = "Comments", name = "Comments", impl = CommentsTable.class) }) 
		})
public class Item extends BaseAttachementableErpModel {

	@Field(caption = "Code", required = true, size = 8, maxLength = 8, validators = { "unique" }, updateable = false, actions = CodeFieldAction.class, actionScope = ActionScope.create)
	@Column(caption = "Code")
	private String code;

	@Field(caption = "Title")
	@Column(caption = "Title")
	private String title;

	@Field(caption = "Description", type = FieldType.textArea,style="height:100px")
	@Column(caption = "Description")
	private String description;

	@Field(caption = "Detail", type = FieldType.textArea)
	private String detail;

	@Field(caption = "Barcode")
	private String barcode;

	@Field(caption = "Base measure unit", type = FieldType.lookup, lookupModel = MeasureUnit.class)
	private MeasureUnit baseMeasureUnit;

	@Column(caption = "Type")
	@Field(caption = "Type", type = FieldType.lookup, lookupModel = ItemType.class)
	private ItemType type;

	@Field(caption = "Selling measure unit", type = FieldType.lookup, lookupModel = MeasureUnit.class)
	private MeasureUnit sellingMeasureUnit;

	@Field(caption = "Category", type = FieldType.lookup, lookupModel = ItemCategory.class)
	private ItemCategory category;

	@Field(caption = "Group", type = FieldType.lookup, lookupModel = ItemGroup.class)
	@Column(caption = "Group")
	private ItemGroup group;

	@Field(caption = "Costing method", type = FieldType.lookup, lookupModel = CostingMethod.class)
	private CostingMethod costingMethod;

	@Field(caption = "Discontinued", type = FieldType.Boolean)
	private Boolean discontinued = false;

	@Field(caption = "Manufactured", type = FieldType.Boolean)
	private Boolean manufactured = false;

	@Field(caption = "VAT Category", type = FieldType.lookup, lookupModel = Vat.class, required = true)
	private Vat vat;

	@Field(caption = "Accounts", type = FieldType.table, lookupModel = AccountAssociations.class)
	private List<AccountAssociations> accountFields = new ArrayList<AccountAssociations>();

	@Field(caption = "Parts", type = FieldType.table, lookupModel = PartLine.class)
	private List<PartLine> parts = new ArrayList<PartLine>();

	@Field(caption = "Shelf No.", maxLength = 5, size = 5)
	private String shelfNo;

	@Field(caption = "Wholesale Selling Price", maxLength = 5, size = 5, type = FieldType.money, required = true)
	private BigDecimal wholesaleSellingPrice;

	@Field(caption = "Wholesale Retail Price", maxLength = 5, size = 5, type = FieldType.money, required = true)
	private BigDecimal retailSellingPrice;

	@Field(caption = "Cost of sales a/c",  style = "width:100px")
	private String costOfSalesAC;

	@Field(caption = "Stock a/c", style = "width:100px")
	private String stockAC;

	@Field(caption = "Sales a/c",  style = "width:100px")
	private String salesAC;

	@Field(caption = "Vat control a/c",  style = "width:100px")
	private String vatControlAC;

	@Field(caption = "Reorder Level", type = FieldType.Float)
	private BigDecimal reorderLevel;

	@Field(caption = "Min reorder Qty", type = FieldType.Float)
	private BigDecimal minReorderQty;

	@Field(caption = "Prefered supplier", type = FieldType.lookup, lookupModel = Supplier.class)
	private Supplier prefferedSupplier;

	@Field(caption = "Allow change price", type = FieldType.Boolean)
	private Boolean allowChangePrice;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public MeasureUnit getBaseMeasureUnit() {
		return baseMeasureUnit;
	}

	public void setBaseMeasureUnit(MeasureUnit baseMeasureUnit) {
		this.baseMeasureUnit = baseMeasureUnit;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public MeasureUnit getSellingMeasureUnit() {
		return sellingMeasureUnit;
	}

	public void setSellingMeasureUnit(MeasureUnit sellingMeasureUnit) {
		this.sellingMeasureUnit = sellingMeasureUnit;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public void setCategory(ItemCategory category) {
		this.category = category;
	}

	public ItemGroup getGroup() {
		return group;
	}

	public void setGroup(ItemGroup group) {
		this.group = group;
	}

	public CostingMethod getCostingMethod() {
		return costingMethod;
	}

	public void setCostingMethod(CostingMethod costingMethod) {
		this.costingMethod = costingMethod;
	}

	public Boolean getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	public String toString() {
		return title;
	}

	public List<AccountAssociations> getAccountFields() {
		return accountFields;
	}

	public void setAccountFields(List<AccountAssociations> accountFields) {
		this.accountFields = accountFields;
	}

	public List<PartLine> getParts() {
		return parts;
	}

	public void setParts(List<PartLine> parts) {
		this.parts = parts;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public Boolean getManufactured() {
		return manufactured;
	}

	public void setManufactured(Boolean manufactured) {
		this.manufactured = manufactured;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}

	public BigDecimal getWholesaleSellingPrice() {
		return wholesaleSellingPrice;
	}

	public void setWholesaleSellingPrice(BigDecimal wholesaleSellingPrice) {
		this.wholesaleSellingPrice = wholesaleSellingPrice;
	}

	public BigDecimal getRetailSellingPrice() {
		return retailSellingPrice;
	}

	public void setRetailSellingPrice(BigDecimal retailSellingPrice) {
		this.retailSellingPrice = retailSellingPrice;
	}

	public String getCostOfSalesAC() {
		return costOfSalesAC;
	}

	public void setCostOfSalesAC(String costOfSalesAC) {
		this.costOfSalesAC = costOfSalesAC;
	}

	public String getStockAC() {
		return stockAC;
	}

	public void setStockAC(String stockAC) {
		this.stockAC = stockAC;
	}

	public String getSalesAC() {
		return salesAC;
	}

	public void setSalesAC(String salesAC) {
		this.salesAC = salesAC;
	}

	public String getVatControlAC() {
		return vatControlAC;
	}

	public void setVatControlAC(String vatControlAC) {
		this.vatControlAC = vatControlAC;
	}

	public BigDecimal getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(BigDecimal reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public BigDecimal getMinReorderQty() {
		return minReorderQty;
	}

	public void setMinReorderQty(BigDecimal minReorderQty) {
		this.minReorderQty = minReorderQty;
	}

	public Supplier getPrefferedSupplier() {
		return prefferedSupplier;
	}

	public void setPrefferedSupplier(Supplier prefferedSupplier) {
		this.prefferedSupplier = prefferedSupplier;
	}

	public Boolean getAllowChangePrice() {
		return allowChangePrice;
	}

	public void setAllowChangePrice(Boolean allowChangePrice) {
		this.allowChangePrice = allowChangePrice;
	}
	
	
	
}
