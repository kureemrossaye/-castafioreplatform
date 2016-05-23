package org.castafiore.ui.ex.dynaform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.panel.EXModal;
import org.castafiore.utils.ComponentUtil;
@SuppressWarnings("rawtypes")
public class EXDynaformPanel extends EXModal implements DynaForm {

	private EXFieldSet body = null;

	public EXDynaformPanel(String name, String title, int columns) {
		super(name, title);
		addClass(getClass().getSimpleName());
		body = new EXFieldSet(name, title, columns);
		body.setShowTitle(false);
		setBody(body);
	}

	public EXDynaformPanel(String name, String title) {
		this(name, title, 1);

	}

	public DynaForm addButton(Button button) {
		setShowFooter(true);
		getFooter().addChild(button);
		return this;
	}

	public DynaForm addField(String label, StatefullComponent input) {
		body.addField(label, input);
		return this;
	}

	public DynaForm addOtherItem(Container input) {

		body.addField(new EXContainer("", "label"), input, 1);
		return this;
	}

	public DynaForm hideField(String name) {
		body.hideField(name);
		return this;
	}

	public DynaForm showField(String name) {
		body.showField(name);
		return this;
	}

	public void setLabelFor(String label, StatefullComponent input) {
		body.setLabelFor(label, input);

	}

	public Map<String, StatefullComponent> getFieldsMap() {
		Map<String, StatefullComponent> result = new HashMap<String, StatefullComponent>();

		for(StatefullComponent stf : this.body.getFields()){
			result.put(stf.getName(), stf);
		}
		return result;
	}

	public StatefullComponent getField(String name) {
		return getFieldsMap().get(name);
	}

	public List<StatefullComponent> getFields() {
		List<StatefullComponent> result = new ArrayList<StatefullComponent>();
		List<Container> children = getBody().getChildren();

		for (Container c : children) {
			StatefullComponent stf = c
					.getDescendentOfType(StatefullComponent.class);
			if (stf != null) {
				result.add(stf);
			}
		}

		return result;
	}

	public Map<String, String> getFieldValues() {
		Map<String, String> result = new HashMap<String, String>();
		List<Container> children = getBody().getChildren();
		for (Container c : children) {
			StatefullComponent stf = c
					.getDescendentOfType(StatefullComponent.class);
			if (stf != null) {
				result.put(stf.getName(), stf.getValue().toString());
			}
		}
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public List<Button> getButtons() {
		Container footer = getFooter();
		List result = new ArrayList();
		ComponentUtil.getDescendentsOfType(footer, result, Button.class);

		return result;
	}
	


}
