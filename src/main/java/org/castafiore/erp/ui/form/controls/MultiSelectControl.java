package org.castafiore.erp.ui.form.controls;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.controls.MagicSelectControl.OnSelectListener;
import org.castafiore.erp.ui.grid.GridDataLocator;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AbstractStatefullComponent;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.hibernate.Session;

public class MultiSelectControl extends AbstractStatefullComponent<List<BaseErpModel>> implements InputControl<List<BaseErpModel>>, OnSelectListener<BaseErpModel>, Event{
	
	private Container selectedList = new EXContainer("selected", "div").addClass("selectedList");
	
	private List<BaseErpModel> data = new ArrayList<BaseErpModel>();
	
	private MagicSelectControl<BaseErpModel> control = null;
	
	private List<OnChangeListener> onChangeListeners = new LinkedList<MultiSelectControl.OnChangeListener>();
	
	public MultiSelectControl(String name, Class<? extends BaseErpModel> lookup, GridDataLocator locator, BigInteger[] status) {
		super(name, "div");
		addClass("MultiSelectControl");
		addChild(selectedList);
		 control = new MagicSelectControl<BaseErpModel>("magic_select", lookup,locator, status);
		 control.addOnSelectListener(this);
		addChild(control);
		setStyle("display", "inline-block");
	}

	
	public void addOnChangeListener(OnChangeListener onChange){
		onChangeListeners.add(onChange);
	}
	
	

	@Override
	public List<BaseErpModel> getValue() {
		
		Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
		for(BaseErpModel model : data){
			if(!session.contains(model))
				session.load(model, model.getId());
		}
		return data;
	}
	
	private Container createNewItem(BaseErpModel model){
		Container item = new EXContainer("div", "div");
		Container delete = new EXIconButton("delete", "delete");
		delete.setAttribute("modelid", model.getId().toString()).setStyle("padding", "8px").setAttribute("title", "Remove item");
		delete.addEvent(this, CLICK);
		item.addChild(delete.setStyle("float", "right"));
		item.setAttribute("modelid", model.getId().toString());
		item.setText(model.toString());
		return item;
	}

	@Override
	public void setValue(List<BaseErpModel> value) {
		this.data = value;
		
		selectedList.getChildren().clear();
		selectedList.setRendered(false);
		
		if(value != null){
		for(BaseErpModel model : value){
			
			selectedList.addChild(createNewItem(model));
		}
		}
		
		
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		control.setEnabled(enabled);
	}

	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		BeanUtil.setProperty(model, getProperty(), data);
	}

	@Override
	public void fillControl(BaseErpModel model) {
		List<BaseErpModel> vals = (List<BaseErpModel>)BeanUtil.getProperty(model, getProperty());
		setValue(vals);
	}
	
	public void fireOnChangeListeners(){
		for(OnChangeListener l : onChangeListeners){
			l.onChange(this);
		}
	}

	public void addItem(BaseErpModel item){
		if(!data.contains(item)){
			data.add(item);
			Container newItem = createNewItem(item);
			selectedList.addChild(newItem);
			fireOnChangeListeners();
		}
	}

	@Override
	public void onSelect(MagicSelectControl<? extends BaseErpModel> source,
			BaseErpModel selected) {
		addItem(selected);
		control.setAttribute("value", "");
		
	}



	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}


	public BaseErpModel getItem(Integer id){
		for(BaseErpModel model : data){
			if(model.getId() == id){
				return model;
			}
		}
		return null;
	}
	
	public void removeItem(Integer id){
		BaseErpModel model = getItem(id);
		boolean removed = false;
		if(model != null){
			data.remove(model);
			removed = true;
		}
		
		
		for(Container c : selectedList.getChildren()){
			if(Integer.parseInt(c.getAttribute("modelid")) == id.intValue()){
				c.remove();
				break;
			}
		}
		
		if(removed)
			fireOnChangeListeners();
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		Integer id = Integer.parseInt(container.getAttribute("modelid"));
		removeItem(id);
		return true;
	}



	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}
	
	public void onReady(JQuery query){
		//query.getDescendentOfType(MagicSelectControl.class).invoke("bt", new JMap().put("trigger",  new JArray().add("focus").add("blur")).put("position", new JArray().add("right")));
	}
	
	public interface OnChangeListener{
		public void onChange(MultiSelectControl control);
	}

}
