package org.castafiore.erp.ui.attachements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseAttachementableErpModel;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.resource.FileData;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.EXUpload.AfterUploadListener;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Directory;

public class Attachements extends EXContainer implements IGroup, TableModel, CellRenderer, AfterUploadListener, Event{
	
	private BaseErpModel data;
	
	private EXTable table;
	
	private static String[] labels =new String[]{"Name", "Action"};
	
	private GridController controller_;

	public Attachements(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller) {
		super("Attachements", "div");
		this.controller_ = controller;
		EXButton refresh = new EXButton("refresh", "Refresh");
		addChild(refresh.addEvent(this, CLICK));
		table = new EXTable("table", this);
		table.setCellRenderer(this);
		addChild(table);
		
	}
 
	@Override
	public InputControl<?> getField(String name) {
		return null;
	}

	@Override
	public void fillModel(BaseErpModel model) {
		((BaseAttachementableErpModel)model).setAttachements(((BaseAttachementableErpModel)data).getAttachements());
		
	}

	@Override
	public void setData(BaseErpModel model) {
		this.data = model;
		table.refreshData();
		
	}

	@Override
	public Map<InputControl<?>, List<String>> getValidators() {
		
		return new HashMap<InputControl<?>, List<String>>();
	}

	@Override
	public void setEnabled(boolean b) {
		
	}

	@Override
	public int getRowCount() {
		if(data == null){
			return 1;
		}
		try{
			return ((BaseAttachementableErpModel)data).getAttachements().size() + 1;
		}catch(Exception e){
			data  = controller_.activate(data);
			return ((BaseAttachementableErpModel)data).getAttachements().size() + 1;
		}
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		try{
		return ((BaseAttachementableErpModel)data).getAttachements().get(row);
		}catch(Exception e){
			
			return null;
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
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		File bf = (File)model.getValueAt(column, row, page);
		if(column == 0){
			
			if(bf != null){
				Container a = new EXContainer("a", "a").setText(bf.getName()).setAttribute("href", ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath())).setAttribute("target", "_blank");
				return a;
			}else{
				EXUpload upl = new EXUpload("upload");
				upl.addAfterUploadListener(this);
				return upl;
				//return new EXUpload("upload");
			}
		}else{
			if(bf != null){
				return new EXIconButton("delete", "delete").setAttribute("row", row + "").setStyle("padding", "8px").addEvent(this, CLICK);
			}else{
				return new EXContainer("", "p").setText("-");
			}
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
	}

	@Override
	public void afterUpload(EXUpload field) {
		List<FileData> uploaded  = field.getValue();
		Directory dir= SpringUtil.getRepositoryService().getDirectory("/root/users", "s");
		for(FileData fd : uploaded){
			File bf = dir.createFile(fd.getName(), File.class);
			try{
			bf.write(fd.getInputStream());
			}catch(Exception e){
				e.printStackTrace();
			}
			bf.save();
			((BaseAttachementableErpModel)data).getAttachements().add(bf);
			System.out.println(bf.getUrl());
		}
		table.refreshData();
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("delete")){
			((BaseAttachementableErpModel)data).getAttachements().remove(Integer.parseInt(container.getAttribute("row")));
			
		}
		
		table.refreshData();
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addValidator(InputControl<?> input, String validator) {
		// TODO Auto-generated method stub
		
	}

}
