package org.castafiore.erp.ui.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.view.ViewFrame;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.erp.workflow.Revision;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.RowDecorator;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;

public class WorkflowHistoryTable  extends EXTable implements TableModel, IGroup, RowDecorator {

	
	private BaseErpModel entity;
	
	List<Revision> data = null;
	
	private List<Number> revisions = new ArrayList<Number>();
	
	private Workflow workflow;
	
	private final Class<? extends BaseErpModel> vo_;
	
	private GridController controller_;
	
	public WorkflowHistoryTable(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller) {
		super("AUD_", null);	
		this.controller_ = controller;
		setRowDecorator(this);
		setHover(true);
		setStriped(true);
		setCondensed(true);
		setBordered(true);
		setStyle("margin", "0 0 9px");
		this.workflow =SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(vo_);
		this.vo_ = vo_;
		
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns().length +3;
	}

	@Override
	public int getRowsPerPage() {
		return 15;
	}

	@Override
	public String getColumnNameAt(int index) {
		if(index == 0){
			return "User";
		}else if(index == 1){
			return "Date modified";
		}else if(index == 2){
			return "Status";
		}
		String field = ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns()[index-3];
		
		return ReflectionUtils.findField(entity.getClass(), field).getAnnotation(Column.class).caption();
		
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(data==null){
			
			
		}
		
		
		int realRow =  row;
		BaseErpModel model = data.get(realRow).getInstance();
		if(col == 0){
			return model.getModifiedBy();
		}else if(col == 1){
			return model.getDateModified();
		}else if(col == 2){
			State state = workflow.getState(model.getStatus());
			return state.getLabel();
		}else{
		
			String field = ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns()[col-3];
		
			return BeanUtil.getProperty(model, field);
		}
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
	public InputControl<?> getField(String name) {
		return null;
	}

	@Override
	public void fillModel(BaseErpModel model) {
		
	}

	@Override
	public void setData(BaseErpModel model) {
		
		this.entity = model;
		
		data =SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflowHistory(entity.getClass(), entity.getId());
		 
		setModel(this);
		  
		setCaption("Workflow history");
		
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

	@Override
	public void decorateRow(final int rowCount, Container row,
			org.castafiore.ui.ex.form.table.Table table, TableModel model) {
		if(row.getEvents()==null || row.getEvents().get(Event.CLICK) == null || row.getEvents().get(Event.CLICK).size() ==0){
		row.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				//
				Class<? extends BaseErpModel> vo =container.getAncestorOfType(WorkflowHistoryTable.class).vo_;
				ViewFrame frame = new ViewFrame(vo,controller_);
				EXPanel panel = new EXPanel("panel", vo.getSimpleName() + " Card");
				panel.setStyle("z-index", "1000").setStyle("width", "800px").setStyle("position", "absolute").setStyle("top", "10%");
				panel.setBody(frame);
				container.getAncestorOfType(IGroup.class).getParent().addChild(panel);
				Revision d = container.getAncestorOfType(WorkflowHistoryTable.class).data.get(rowCount);
				//
				frame.setData(d);
				return true;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				container.server(this);
				
			}
		}, Event.CLICK);
		}
		
	}
	
	

}
