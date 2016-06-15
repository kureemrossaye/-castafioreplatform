package org.castafiore.erp.ui.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

public class AuditTable  extends EXTable implements TableModel, IGroup {

	
	private BaseErpModel entity;
	
	List<BaseErpModel> data = null;
	
	private List<Number> revisions = new ArrayList<Number>();
	
	public AuditTable(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller) {
		super("AUD_", null);	
		setHover(true);
		setStriped(true);
		setCondensed(true);
		setBordered(true);
		setStyle("margin", "0 0 9px");
		
	}

	@Override
	public int getRowCount() {
		return revisions.size();
	}

	@Override
	public int getColumnCount() {
		return ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns().length +2;
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
		}
		String field = ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns()[index-2];
		
		return ReflectionUtils.findField(entity.getClass(), field).getAnnotation(Column.class).caption();
		
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(col == 0 && row==0){
			//start or new page
			AuditReader reader = AuditReaderFactory.get(SpringUtil.getBeanOfType(Dao.class).getReadOnlySession());
			int start = page*getRowsPerPage();
			int end = start + getRowsPerPage();
			if(revisions.size() <= (start + end)){
				end = revisions.size();
			}
			data = new ArrayList<BaseErpModel>();
			 for(int i= start; i < end; i++){
				 BaseErpModel item = reader.find(entity.getClass(), entity.getId(), revisions.get(i));
				 data.add(item);
			 }
			//setModel(this);
		}
		
		
		int realRow =  row;
		BaseErpModel model = data.get(realRow);
		if(col == 0){
			return model.getModifiedBy();
		}else if(col == 1){
			return model.getDateModified();
		}else{
		
			String field = ReflectionUtils.getAnnotation(entity.getClass(),Table.class).columns()[col-2];
		
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
		if(entity.getId() != null){
			AuditReader reader = AuditReaderFactory.get(SpringUtil.getBeanOfType(Dao.class).getReadOnlySession());
			revisions = reader.getRevisions(entity.getClass(), entity.getId());
		}else{
			revisions.clear();
		}
		 
		 
		  
		setCaption("History");
		setModel(this);
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
