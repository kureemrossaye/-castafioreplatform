package org.castafiore.portal.ui.data;

import java.util.Map;

import org.castafiore.portal.ui.widgets.EXWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;

public abstract class EXDataForm<T> extends EXWidget implements DataForm<T>, Event {

	private Container btnsContainer = new EXContainer("btnsContainer", "div").addClass("stdformbutton");

	protected EXDataGrid<T> table_ = null;

	protected EXButton save = new EXButton("save", "Save");

	protected EXButton cancel = new EXButton("cancel", "Cancel");

	public EXDataForm(String name, String title) {
		super(name, title);
	}

	public EXDataForm<T> addField(String label, StatefullComponent<?> field) {

		Container p = new EXContainer(field.getName() + "_wrapper", "p").addClass("wrapper");

		getWidgetContainer().addChild(p);

		Container uilabel = new EXContainer(field.getName() + "_label", "label").setText(label);
		p.addChild(uilabel);

		Container span = new EXContainer(field.getName() + "_span", "span").addClass("field")
				.addChild(field.addClass("input-xxlarge"));

		p.addChild(span);

		addChild(btnsContainer);
		btnsContainer.addChild(save.addEvent(this, CLICK)).addChild(cancel.addEvent(this, CLICK));
		return this;
	}

	public void hideSave(boolean hide) {
		save.setDisplay(!hide);
	}

	public void hideCancel(boolean hide) {
		cancel.setDisplay(!hide);
	}

	public EXDataForm<?> addButton(EXButton btn) {
		btnsContainer.addChild(btn);
		return this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StatefullComponent getField(String name) {
		return (StatefullComponent<Object>) getDescendentByName(name);
	}

	public void setDataGrid(EXDataGrid<T> grid) {
		this.table_ = grid;
	}

	public Container getBtnsContainer() {
		return btnsContainer;
	}

	public EXButton getSave() {
		return save;
	}

	public EXButton getCancel() {
		return cancel;
	}

	public void save() {
		getModel();
		this.table_.finish();
		
	}

	public void cancel() {
		this.table_.cancel();
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		if(container.equals(save)){
			save();
		}else if(container.equals(cancel)){
			cancel();
		}
		
		return true;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

}
