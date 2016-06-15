package org.castafiore.erp.ui.vat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Vatable;
import org.castafiore.erp.ui.form.FieldSet;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.DateControl;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.layout.EXMigLayout;

public class VatTable extends EXMigLayout implements IGroup , TableModel, Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String[] LABELS = new String[]{"Date","Doc no.","Type","Vat", "Total","Status"};
	
	private final static String[] fields = new String[]{"date", "referenceNumber", "documentType", "totalVat", "total", "status"};
	
	private FieldSet filter = new FieldSet("filter");
	
	private DateControl from;
	
	private DateControl to;
	
	private EXTable table = null;
	
	private List<Vatable> data = new ArrayList<Vatable>();

	public VatTable(String name) {
		super(name, "6:6;12");
		addChild(filter, "0:0");
		from = new DateControl("from");
		filter.addField("From", from);
		
		to = new DateControl("to");
		filter.addField("To",to);
		
		to.addEvent(this, Event.BLUR);
		
		EXButton btn = new EXButton("search", "Search");
		
		filter.addChild(btn.addEvent(this, CLICK));
		
		table = new EXTable("table", this);
		
		addChild(table, "0:1");
		
	}

	@Override
	public InputControl<?> getField(String name) {
		return null;
	}

	@Override
	public void fillModel(BaseErpModel model) {
		
	}

	@Override
	public void setData(BaseErpModel model) {
		
	}

	@Override
	public int getRowCount() {
	return data.size();
	}

	@Override
	public int getColumnCount() {
		return LABELS.length;
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public String getColumnNameAt(int index) {
		return LABELS[index];
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		
		int realRow = (page*getRowsPerPage()) + row;
		
		Vatable obj = data.get(realRow);
		
		return BeanUtil.getProperty(obj, fields[col]);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String sql = "from " + Vatable.class.getName() ;
		
		data = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createQuery(sql).list();
		
		table.setModel(this);
		return true;
		
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	@Override
	public Map<InputControl<?>, List<String>> getValidators() {
		return new HashMap<InputControl<?>, List<String>>(0);
	}

	@Override
	public void setEnabled(boolean b) {
		
	}

	@Override
	public void addValidator(InputControl<?> input, String validator) {
		// TODO Auto-generated method stub
		
	}

}
