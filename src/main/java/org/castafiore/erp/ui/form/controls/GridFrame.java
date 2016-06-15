package org.castafiore.erp.ui.form.controls;
import static org.castafiore.erp.utils.ReflectionUtils.getAnnotation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Organization;
import org.castafiore.erp.Process;
import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.SalesOrderLine;
import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.search.SearchResponse;
import org.castafiore.erp.ui.conference.Sychronize;
import org.castafiore.erp.ui.form.ActionInterceptor;
import org.castafiore.erp.ui.form.Error;
import org.castafiore.erp.ui.form.ErrorPopup;
import org.castafiore.erp.ui.form.Forms;
import org.castafiore.erp.ui.form.ValidationException;
import org.castafiore.erp.ui.grid.DefaultDataLocator;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.grid.GridDataLocator;
import org.castafiore.erp.ui.grid.QueryParams;
import org.castafiore.erp.ui.grid.WorkflowColumn;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.erp.utils.Util;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.RowDecorator;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.layout.EXMigLayout;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

import jodd.bean.BeanUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

public class GridFrame extends EXMigLayout implements TableModel, CellRenderer,
		RowDecorator, Event, TableColumnModel,
		StatefullComponent<List<BaseErpModel>>,
		InputControl<List<BaseErpModel>> , CastafioreController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EXTable grid;

	private Forms form;

	private GridController controller = new GridController();

	private GridDataLocator locator = new DefaultDataLocator();

	private Class<? extends BaseErpModel> vo;

	private Table tableDefn;

	private SearchResponse data = null;

	private final static Map<Class<?>, Map<String, Field>> CACHE = new HashMap<Class<?>, Map<String, Field>>();

	Map<String, Field> fields = new HashMap<String, Field>();

	private Workflow workflow = null;

	private boolean vertical_ = false;

	private QueryParams params = new QueryParams();
	
	
	EXButton refresh = new EXButton("refresh", "Refresh");
	
	EXButton showAll = new EXButton("showAll", "Show All");
	
	EXButton addNew = new EXButton("insert", "Add new");
	
	private EXToolBar formToolbar;
	
	private ErrorPopup errorpopup = new ErrorPopup();
	
	public GridFrame(Class<? extends BaseErpModel> vo) {
		this(null, vo);
	}

	public GridFrame(String field, Class<? extends BaseErpModel> vo) {
		super(field != null ? field : vo.getSimpleName(),
				field != null ? "12;12" : "6:6");
		addClass("w2ui-page");
		this.vo = vo;
		this.tableDefn = getAnnotation(vo,Table.class);
		workflow = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(vo);
		
		this.vertical_ = field != null;

		jbInit();
	}

	public GridFrame(Class<? extends BaseErpModel> vo, GridDataLocator locator) {
		super(vo.getSimpleName(), "6:6");
		this.locator = locator;
		addClass("w2ui-page");
		this.vo = vo;
		this.tableDefn = getAnnotation(vo,Table.class);
		workflow =SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(vo);
		

		jbInit();
	}

	public void setShowSearch(boolean b) {
	}

	public void setShowForm(boolean b) {
		form.setDisplay(b);
	}
	
	

	public void setData(List<BaseErpModel> data) {
		SearchResponse response = new SearchResponse();
		response.setCount(data.size());
		response.setData(data);
		this.data = response;
		grid.refresh();
		if(vertical_){
			addNew();
		}
	}

	
	public EXButtonSet getMainFormSet(){
		return getFormSet("set1");
	}
	
	
	public EXButtonSet getFormSet(String name){
		for(Container c : formToolbar.getChildren()){
			if(c.getName().equals(name)){
				return (EXButtonSet)c;
			}
		}
		return null;
	}
	
	private void jbInit() {
		
		
		addChild(errorpopup.setDisplay(false));
		data = new SearchResponse();//locator.loadData(vo, params);
		
		grid = new EXTable("table", this);
		grid.setStriped(true);
		grid.setHover(true);
		grid.setCondensed(true);
		grid.setBordered(true);
		grid.setStyle("width", "100%");
		grid.addClass("table");
		grid.setCellRenderer(this);
		grid.setRowDecorator(this);
		grid.setColumnModel(this);

		if(vertical_)
			grid.setStyle("margin", "0px");
		

		EXButtonSet set = new EXButtonSet("tableset");
		set.addClass("btn-group-xs");
		
		if (vertical_) {
			set.addItem(new EXButton("save", "Save"));
			set.getChild("save").addEvent(this, CLICK);
		}

		
		showAll.addEvent(this, CLICK);
		
		if(!vertical_){
			set.addItem(addNew);
			addNew.addEvent(this, CLICK);
			refresh.addEvent(this, CLICK);
			
			set.addItem(refresh);
			set.addItem(showAll);
		}
		//EXHelp help = new EXHelp(vo);
		//help.addcl
		//help.addClass("pull-right");
		//set.addItem(help);
		

		addChild(set.setStyle("margin", "8px 0 7px"), "0:0");
		addChild(grid, "0:0");
		form = new Forms(getAnnotation(vo,org.castafiore.erp.annotations.Forms.class),vo, controller);

		if (!vertical_) {
			form.setStyle("margin", "0 8px");
			formToolbar = new EXToolBar("toolbar");

			formToolbar.setStyleClass("s");
			formToolbar.setAttribute("style","background-color: transparent; margin: 8px;");
			EXButtonSet set1 = new EXButtonSet("set1");
			set1.addClass("btn-group-xs");
			set1.addItem(new EXButton("insert", "New"));
			set1.addItem(new EXButton("save", "Save"));
			Container preview = new EXContainer("preview", "a").setAttribute("href", ResourceUtil.getMethodUrl(this)).setAttribute("target", "_blank").setText("Print").addClass("btn").addClass("btn-default");
			set1.addChild(preview);
			set1.getChild("insert").addEvent(this, CLICK);
			set1.getChild("save").addEvent(this, CLICK);
			//set1.getChild("preview").addEvent(this, CLICK);

//			EXButtonSet set2 = new EXButtonSet("set2");
//			set2.addClass("btn-group-xs");
//			set2.addItem(new EXButton("synch", "Synch Users"));

			formToolbar.addItem(set1);
			//formToolbar.addItem(set2);
			//set2.getChild("synch").addEvent(this, CLICK);

			EXButtonSet set4 = new EXButtonSet("set4");
			set4.addClass("btn-group-xs").addClass("pull-right");
			EXButton close = new EXButton("close", "Close");
			
			
			set4.addItem(close);
			close.addEvent(this, CLICK);
			close.addClass("pull-right");
			formToolbar.addItem(set4);
			addChild(formToolbar, "1:0");
		}
		
		
		addChild(form, vertical_ ? "0:1" : "1:0");
		
		
		
		grid.addHeaderStyle("btn-primary");
		maximizeGrid();
		if(vertical_){
			addNew();
		}
	}
	public EXToolBar getFormToolbar(){
		return formToolbar;
	}
	
	
	
	public EXButtonSet addFormButtonSet(EXButtonSet buttonSet){
		for(Container c : formToolbar.getChildren()){
			if(c.getName().equals(buttonSet.getName())){
				return (EXButtonSet)c;
			}
		}
		formToolbar.addItem(buttonSet);
		return buttonSet;
	}

	public void minimizeGrid() {
		if (!vertical_) {
			setWidth(3, "0:0");
			setWidth(9, "1:0");
			getContainer("1:0").setDisplay(true);
			grid.showMaxFields(2);

		}
	}

	public void maximizeGrid() {
		if (!vertical_) {
			setWidth(12, "0:0");
			getContainer("1:0").setDisplay(false);
			grid.showAllFields();
		}
	}

	@Override
	public int getRowCount() {
		if (data == null) {
			data = locator.loadData(vo, params);
		}
		return data.getCount();
	}

	@Override
	public int getColumnCount() {
		if (workflow != null)
			return tableDefn.columns().length + 1;

		return tableDefn.columns().length;

	}

	@Override
	public int getRowsPerPage() {
		return tableDefn.pageSize();
	}

	public static Field getField(String property, Class<?> clazz) {
		if (CACHE.containsKey(clazz)) {
			if (CACHE.get(clazz).containsKey(property)) {
				return CACHE.get(clazz).get(property);
			} else {
				Field f = ReflectionUtils.findField(clazz, property)
						.getAnnotation(Field.class);
				CACHE.get(clazz).put(property, f);
				return f;
			}
		} else {
			Map<String, Field> field = new HashMap<String, Field>();
			Field f = ReflectionUtils.findField(clazz, property).getAnnotation(Field.class);
			field.put(property, f);
			CACHE.put(clazz, field);
			return f;
		}
	}

	@Override
	public String getColumnNameAt(int index) {
		if (workflow != null && index == getColumnCount() - 1) {
			return "Actions";
		}
		String property = tableDefn.columns()[index];
		System.out.println(property);
		Column f = ReflectionUtils.findField(vo, property).getAnnotation(
				Column.class);
		if (f == null) {
			throw new RuntimeException("annotation " + Column.class.getName()
					+ " missing on field " + property + " in class "
					+ vo.getName());
		}
		return f.caption();
	}

	public void search(String term) {
		params.setFullTextSearchTerm(term);
		params.setPage(0);
		data = null;
		this.grid.refreshData();
	}
	
	public EXButton getAddNewButton(){
		return addNew;
	}
	

	@Override
	public Object getValueAt(int col, int line, int page) {

		if(col == 0 && line ==0){
			params.setPage(page);
			if(!vertical_)
				data = locator.loadData(vo, params);
		}
		
		
		if (workflow != null && col == getColumnCount() - 1) {
			// last col. show status

			BigInteger status = data.getData().get(line).getStatus();
			if (status == null) {
				status = BigInteger.ONE;
			}

			State state = workflow.getState(status);
			if (state != null) {
				Container badge = new EXContainer("badge", "span")
						.setText(state.getLabel()).addClass("badge pull-right")
						.setStyle("background-color", state.getColor()).setStyle("cursor", "pointer");
				EXButtonSet set = new EXButtonSet("actions");
				for (org.castafiore.erp.Process act : state.getActions()) {
					EXButton btn = new EXButton(act.getId() + "", act.getLabel());
					btn.addEvent(this, CLICK);
					btn.setAttribute("tosss", act.getTarget().getValue().toString()).setAttribute("row", line + "");
					btn.setSize(EXButton.SIZE_EXTRA_SMALL);
					set.addChild(btn);
				}

				Container uis = new EXContainer("ui", "div");
				uis.addChild(badge);
				uis.addChild(set);
				return uis;
			}

			return new EXContainer("ui", "div");
		}

		String property = tableDefn.columns()[col];

		return BeanUtil.getProperty(data.getData().get(line), property);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		String property = tableDefn.columns()[columnIndex];
		return ReflectionUtils.findField(vo, property).getType();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		String property = tableDefn.columns()[columnIndex];
		return getField(property, vo).editable();
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object data = model.getValueAt(column, row, page);
		if (data instanceof Container) {
			return (EXContainer) data;
		}

		if (data == null) {
			return new EXContainer("", "span").setText("");
		} else {
			if (data instanceof Boolean) {
				return new EXCheckBox("", (Boolean) data);
			} else if (data instanceof Date) {
				return new EXContainer("", "span")
						.setText(new SimpleDateFormat("dd-MMM-yyyy")
								.format((Date) data));
			} else {
				return new EXContainer("", "span").setText(data.toString());
			}
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Object data = model.getValueAt(column, row, page);
		if (data instanceof Container) {
			Container parent = component.getParent();
			component.remove();
			parent.setRendered(false);
			parent.addChild((Container) data);
			return;
		}
		if (data == null) {
			component.setText("");
		} else {
			if (data instanceof Boolean) {
				((EXCheckBox) component).setChecked((Boolean) data);
			} else if (data instanceof Date) {
				component.setText(new SimpleDateFormat("dd-MMM-yyyy")
						.format((Date) data));
			} else {
				component.setText(data.toString());
			}
		}

	}
	
	public void addNew(){
		minimizeGrid();
		try {
			
			this.form.setData(vo.newInstance());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}
	
	
	public void addActionInterceptor(ActionInterceptor<?> interceptor){
		form.addActionInterceptor(interceptor);
		
		BigInteger status = form.getDefaultStatus();
		if(status != null){
			String label = workflow.getState(status).getLabel();
			getAddNewButton().setText("Add new " +label);
		}
	}
	
	public void save(){
		try {
			//errors.clear();
			errorpopup.clear();
			if (form.isNew()) {
				BaseErpModel created = form.saveOrUpdate();
				if(vertical_){
					data.getData().add(created);
					data.setCount(data.getData().size());
					//params.addToConcat(created);
				}
				//params.addToConcat(created);
			} else {
				form.saveOrUpdate();
			}
			if(!vertical_)
				data = null;
			
			grid.refreshData();

			minimizeGrid();
			grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
			if(vertical_){
				addNew();
			}
			errorpopup.setDisplay(false);
		}catch(ValidationException ve){
			
			
			for(Error e : ve.getError()){
				errorpopup.addError(e.getMessage());
			}
			
			errorpopup.setDisplay(true);
			
			//errors.addAll(ve.getError());
		}
		catch (Exception e) {
			throw new UIException(e);
		}
	}
	
	public void process(int row, int id){
		org.castafiore.erp.Process p = Util.get(id, Process.class);
		
		BaseErpModel model = data.getData().get(row);
		controller.activate(model);
		try {
			SpringUtil.getBeanOfType(WorkflowManager.class).process(p, model);
			grid.refreshData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search(Date from, Date to, String field){
		params.addRestriction(field, from, to);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	public void search(List<BigInteger> status){
		params.setStatus(status);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	 
	public void search(BigInteger from, BigInteger to, String field){
		params.addRestriction(field, from, to);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	public void search(BigDecimal from, BigDecimal to, String field){
		params.addRestriction(field, from, to);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	
	public void search(String term, String field){
		params.addRestriction(field, term);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	
	public void search(List<BaseErpModel> in,String field){
		params.addRestriction(field, in);
		data = null;
		grid.refreshData();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	public void refreshData(){
		data = null;
		grid.refreshData();
		maximizeGrid();
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	public void showAll(){
		params.setShowAll(true);
		data = null;
		grid.refreshData();
		maximizeGrid();
		
		grid.selectRow(grid.getChild("tbody").getChildByIndex(0));
	}
	
	public void edit(int row){
		BaseErpModel model = data.getData().get(row);
		controller.activate(model);
		

		this.form.setData(model);
		minimizeGrid();
		Container container = this.grid.getTd(0, row).getParent();
		grid.selectRow(container);
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("synch")){
			try{
			Sychronize.synch();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
			return true;
		}
		
		if(container.getName().equals("searchInput")){
			
			search(container.getAncestorOfType(EXInput.class).getValue());
			return true;
		}

		if (container.getParent().getName().equals("actions")) {

			int row = Integer.parseInt(container.getAttribute("row"));
			int id = Integer.parseInt(container.getName());
			
			process( row,id);

			return true;
		}

		if (container.getName().equals("addNew")|| container.getName().equals("insert")) {
			addNew();
			return true;

		} else if (container.getName().equals("save")) {

			save();
			return true;
		} else if (container.getName().equalsIgnoreCase("refresh")) {
			refreshData();
			return true;
		}else if (container.getName().equalsIgnoreCase("showAll")) {
			showAll();
			return true;
		}

		if (container.getName().equals("close")) {
			maximizeGrid();
			return true;
		}

		int row = Integer.parseInt(container.getAttribute("row"));
		
		edit(row);
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
	}
	
	public InputControl<?> getField(String name){
		return form.getField(name);
	}

	private Event MAXIMIZE = new Event() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void Success(JQuery container, Map<String, String> request)
				throws UIException {

		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			maximizeGrid();

			return true;
		}

		@Override
		public void ClientAction(JQuery container) {
			container.server(this);

		}
	};

	@Override
	public void decorateRow(int rowCount, Container row,
			org.castafiore.ui.ex.form.table.Table table, TableModel model) {
		if (row.hasEvent(getClass(), Event.CLICK)) {

		} else {
			row.setAttribute("row", rowCount + "");
			row.addEvent(this, CLICK);
			// row.addEvent(MAXIMIZE, DOUBLE_CLICK);
			// row.addEvent(this, KEY_DOWN);
		}

	}

	@Override
	public Container getColumnAt(int index,	org.castafiore.ui.ex.form.table.Table table, TableModel model) {

		if (index == getColumnCount() - 1 && workflow != null) {
			
			WorkflowColumn th = new WorkflowColumn(workflow, this);
			return th;
		} else {
			Field prop = getField(this.tableDefn.columns()[index], vo);
			java.lang.reflect.Field f = ReflectionUtils.findField(vo, this.tableDefn.columns()[index]);
			Column column = f.getAnnotation(Column.class);
			String field = f.getName();
			GridColumn col = new GridColumn(field, column, vo, prop, f, grid,this);
			return col;
		}

	}


	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		BeanUtil.setProperty(model, getProperty(), getValue());

	}

	@SuppressWarnings("unchecked")
	@Override
	public void fillControl(BaseErpModel model) {
		setValue((List<BaseErpModel>) BeanUtil
				.getProperty(model, getProperty()));

	}

	@Override
	public List<BaseErpModel> getValue() {
		return data.getData();
	}

	@Override
	public void setValue(List<BaseErpModel> value) {
		setData(value);
		//grid.refreshData();

	}

	@Override
	public void setEnabled(boolean enabled) {

	}
	
	
	public Map<String, Object> getReportParameters (){
		Organization o = Util.getOrganization();
		SalesOrder order = (SalesOrder)this.form.getData();
		State s  = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(SalesOrder.class).getState(order.getStatus());
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("CompanyName", o.getCompanyName());
		p.put("CompanySlogan", o.getSlogan());
		p.put("AddressLine1", o.getAddress().getAddressLine1());
		p.put("AddressLine2", o.getAddress().getAddressLine2());
		p.put("Town", o.getAddress().getCity());
		p.put("Country", o.getAddress().getCountry());
		p.put("Telephone", o.getPhone());
		p.put("Fax", o.getFax());
		
		p.put("Email", o.getEmail());
		p.put("BRN", o.getBrn());
	
		
		p.put("InvoiceNo", order.getReferenceNumber());
		p.put("Date", order.getDate());
		p.put("CustomerName", order.getCustomer().getCompanyName());
		p.put("BillingAddressLine1", order.getBillingAddress().getAddressLine1());
		p.put("BillingAddressLine2", order.getBillingAddress().getAddressLine2());
		p.put("BillingAddressTown", order.getBillingAddress().getCity());
		p.put("BillingAddressCountry", order.getBillingAddress().getCountry());
		p.put("CustomerTelephone", order.getCustomer().getContacts().get(0).getPhone());
		p.put("CustomerEmail", order.getCustomer().getContacts().get(0).getEmail());
		p.put("CustomerMobile",order.getCustomer().getContacts().get(0).getMobile());
		p.put("SubTotal", order.getTotalDiscount().toPlainString());
		p.put("TotalTax", order.getTotalVat().toPlainString());
		p.put("Total", order.getTotal().toPlainString());
		p.put("DocumentTitle", s.getLabel());
		p.put("VATRegNo", o.getVatNo());
		p.put("ShippingAddressLine1", o.getShipToAddress().getAddressLine1());
		p.put("ShippingAddressLine2", o.getShipToAddress().getAddressLine2());
		p.put("ShippingAddressTown", o.getShipToAddress().getCity());
		p.put("ShippingAddressCountry", o.getShipToAddress().getCountry());
		
		return p;
		
		
	}
	
	
	public List<Map<String, ?>> getReportData(){
		List<Map<String, ?>> result = new LinkedList<Map<String,?>>();
		int i =1;
		SalesOrder o = (SalesOrder)form.getData();
		controller.activate(o);
		for(SalesOrderLine line : o.getLines()){
			Map<String, Object> data = new HashMap<String, Object>(8);
			data.put("ItemNo",i+"" );
			data.put("ProductCode",line.getItem().getCode() );
			data.put("ProductDescription",line.getItem().getTitle() );
			data.put("Quantity",line.getQuantity() );
			data.put("UnitPrice",line.getTotal() );
			data.put("Amount",line.getTotal() );
			data.put("DiscountedPrice",line.getTotalDiscount() );
			data.put("VAT",line.getTotalVat());
			i++;
			result.add(data);
		}
		return result;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		//String template = request.getParameter("template");
		String template = "/usr/local/software/tomcat6/webapps/flexisoft/Sales_files/Invoice.jasper";
		
		//JasperReport report = (JasperReport)JRLoader.loadObject(new File(template));
		JRMapCollectionDataSource datasource = new JRMapCollectionDataSource(getReportData());
		JasperPrint print = JasperFillManager.fillReport(template, getReportParameters(), datasource);
		
		JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
		
	
		return null;
	}

}
