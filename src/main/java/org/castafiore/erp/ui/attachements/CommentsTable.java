package org.castafiore.erp.ui.attachements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.CommentableErpModel;
import org.castafiore.erp.UserComment;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class CommentsTable extends EXContainer implements IGroup, TableModel, CellRenderer, Event{
	
	
	private List<UserComment> comments = new ArrayList<UserComment>();
	
	private EXTable table;
	
	public CommentsTable(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller){
		super("CommentsTable", "div");
		this.table = new EXTable("table", this);
		table.setCellRenderer(this);
		addChild(table);
	}
	

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("delete")){
			comments.remove(Integer.parseInt(container.getAttribute("row")));
			table.setModel(this);
		}else{
			String comment = container.getParent().getDescendentOfType(EXTextArea.class).getValue();
			if(StringUtil.isNotEmpty(comment)){
				UserComment com = new UserComment();
				com.setText(comment);
				//SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(com);
				try{
				new GridController().insertRecord(com);
				comments.add(com);
				table.setModel(this);
				}catch(Exception e){
					throw new UIException(e);
				}
			}
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		
		UserComment comment = (UserComment)getValueAt(column, row, page);
		
		if(comment != null){
			if(column == 0){
				Container uicomment = new EXContainer("cc", "div");
				uicomment.addChild(new EXContainer("", "i").setText("Created by : ")).addChild(new EXContainer("from", "label").setText(comment.getCreatedBy()));
				uicomment.addChild(new EXContainer("cc", "p").setStyle("border", "solid 1px silver").setStyle("padding", "8px").setText(comment.getText()));
				return uicomment;
			}else{
				return new EXIconButton("delete", "delete").setAttribute("row", row + "").addEvent(this, CLICK).setStyle("padding", "8px").setAttribute("title", "Delete this comment");
			}
		}else{
			if(column == 0){
				Container uicomment = new EXContainer("cc", "div");
				//uicomment.addChild(new EXText("from", "label").setText(comment.getCreatedBy()));
				uicomment.addChild(new EXTextArea("cc").setStyle("border-radius", "0").setStyle("margin-bottom", "7px")).addChild(new EXButton("save", "Save").addClass("pull-right").addEvent(this, CLICK).addClass("btn-xs")).addChild(new EXButton("cancel", "Cancel").addClass("btn-xs").addClass("pull-right").addEvent(this, CLICK));
				return uicomment;
			}else{
				return new EXContainer("p", "p").setText("-");
			}
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
	}

	@Override
	public int getRowCount() {
		return comments.size() + 1;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowsPerPage() {
		return comments.size()+1;
	}

	@Override
	public String getColumnNameAt(int index) {
		if(index==0){
			return "Comment";
		}else{
			return "Action";
		}
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		try{
			return comments.get(row);
			
			
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
	public InputControl<?> getField(String name) {
		return null;
	}

	@Override
	public void fillModel(BaseErpModel model) {
		((CommentableErpModel)model).setComments(comments);
		
		
	}

	@Override
	public void setData(BaseErpModel model) {
		this.comments=((CommentableErpModel)model).getComments();
		table.refresh();
		
	}

	@Override
	public Map<InputControl<?>, List<String>> getValidators() {
		return new HashMap<InputControl<?>, List<String>>();
	}

	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addValidator(InputControl<?> input, String validator) {
		// TODO Auto-generated method stub
		
	}

}
