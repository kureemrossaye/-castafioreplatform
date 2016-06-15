package org.castafiore.erp.ui.form;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.ui.form.controls.ControlsUtil;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.AssociationTable;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.HelpUtil;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButtonSet;

@SuppressWarnings("serial")
public class Group extends EXContainer implements IGroup, PopupContainer{
	
	
	private Map<String, InputControl<?>> fields = new HashMap<String, InputControl<?>>();
	
	
	private Map<InputControl<?>, List<String>> validators = new HashMap<InputControl<?>, List<String>>();
	
	public static String getTag(org.castafiore.erp.annotations.Group group){
		if(group.displayType() == DisplayType.FORM){
			return "fieldset";
		}else{
			return "div";
		}
	}
	
	
	public void addValidator(InputControl<?> input, String validator){
		if(validators.containsKey(input)){
			if(!validators.get(input).contains(validator))
				validators.get(input).add(validator);
		}else{
			validators.put(input, new LinkedList<String>());
			addValidator(input, validator);
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Group(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller) {
		super(group.name(), getTag(group));
		
		if(group.displayType() == DisplayType.TABLE){
			for(String field : group.fields()){
				AssociationTable frame = new AssociationTable(field, vo_, controller);
				frame.addClass("w2ui-group");
				frame.init();
				addChild(frame);
				fields.put(field, frame);
				
			}
		}else if(group.displayType() == DisplayType.TABLE_FORM){
			
			for(String field : group.fields()){
				java.lang.reflect.Field rField = ReflectionUtils.findField(vo_, field);
				Field annField = rField.getAnnotation(Field.class);
				Class lookup = annField.lookupModel();
				GridFrame frame = new GridFrame(field, lookup);
				addChild(frame);
				fields.put(field, frame);
				
			}
			
		}else{
	
			Container uititle = new EXContainer("title", "legend").setStyle("padding", "3px").setStyle("font-weight", "bold").setStyle("color", "#777");
			addChild(uititle.setText(group.label()));
			uititle.setDisplay(group.showTitle());
			
			Container cont = new EXContainer("cont", "div").addClass("w2ui-group");
			addChild(cont);
			
			for (String field : group.fields()) {
				java.lang.reflect.Field myfield = ReflectionUtils.findField(vo_, field);
				if(myfield == null){
					throw new RuntimeException("The field " + field + " cannot be found in " + vo_.getName());
				}
				Field prop = myfield.getAnnotation(Field.class);
				InputControl<?> uifield =ControlsUtil.getField(field, prop, this, controller);
				
				fields.put(uifield.getName(), uifield);
				
				ControlsUtil.applyInterceptors(uifield, this, prop);
				ControlsUtil.applyValidators(uifield, this, prop);
				
				Container uilabel = new EXContainer("label", "div").addClass("w2ui-label").setText(prop.caption());
				
				uilabel.setStyle("width", prop.labelWidth());
				
				Container uifieldcont = new EXContainer("field", "div").addClass("w2ui-field").addChild(uifield);
				
				uifieldcont.setStyle("width", prop.fieldWidth());
				if(uifield instanceof EmbededForm){
					uilabel.setDisplay(false);
					uifieldcont.setStyle("width", "100%");
				}
			
				String help = HelpUtil.getFieldHelp(vo_, field);
				uifield.setAttribute("title", help);
				cont.addChild(uilabel);
				cont.addChild(uifieldcont);
				
				if(!prop.actions().equals(NoFieldAction.class)){
					try{
					FieldAction action = prop.actions().newInstance();
					
					EXButtonSet set = action.getButtons(uifield, this,vo_);
					if(set != null){
						uilabel.addChild(set);
					}
					
					}catch(Exception e){
						throw new UIException(e);
					}
					
				}
				//do the action part
			}
		}
		
	}
	
	
	public Map<InputControl<?>, List<String>> getValidators(){
		return validators;
	}
	
	public InputControl<?> getField(String name){
		return fields.get(name);
	}
	public void fillModel(final BaseErpModel model){
		for(String name : fields.keySet()){
			InputControl<?> field = fields.get(name);
//			java.lang.reflect.Field f = ReflectionUtils.findField(model.getClass(), name);
//			if(f != null){
//				Field annotation = f.getAnnotation(Field.class);
//				if(annotation.required()){
//					if(field.getValue() == null || (f.getType().equals(String.class) && field.getValue().toString().trim().length() == 0)){
//						//
//					}
//				}
//			}
			
			field.fillVo(model);
		}
		
	}
	public void setData(final BaseErpModel model){
			
			for(String name : fields.keySet()){
				InputControl<?> field = fields.get(name);
				field.fillControl(model);
				
				java.lang.reflect.Field rField = ReflectionUtils.findField(model.getClass(), name);
				Field anot = rField.getAnnotation(Field.class);
				boolean updatable = anot.updateable();
				if(model.getId() != null && !updatable){
					field.setEnabled(false);
				}else{
					field.setEnabled(true);
				}
				
				try{
					ActionScope scope = anot.actionScope();
					
					if(!anot.actions().equals(NoFieldAction.class)){
						String display = "inline-block";
						if(true){
							if((model.getId() == null && scope == ActionScope.edit) || (model.getId() != null && scope==ActionScope.create)){
								display = "none";
							}
							
							
							Container label = getLabelContainerFor(field);
							if(label.getChildren().size() > 0){
								EXButtonSet set = (EXButtonSet)label.getChildren().get(0);
								if(model.getId() == null){
									anot.actions().newInstance().changeMode(set, ActionScope.create, field, this,null);
								}else{
									anot.actions().newInstance().changeMode(set, ActionScope.edit, field, this,null);
								}
								label.getChildren().get(0).setStyle("display", display);
							}
						}
					}
				}catch(Exception e){
					throw new UIException(e);
				}
			}
		
	}
	
	public Container getLabelContainerFor(InputControl<?> field){
		Container fieldcont = field.getParent();
		Container fieldset = fieldcont.getParent();
		Container label = fieldset.getChildByIndex( fieldset.getChildren().indexOf(fieldcont)-1);
		return label;
	}


	@Override
	public void setEnabled(boolean b) {
		for(String name : fields.keySet()){
			fields.get(name).setEnabled(b);
		}
		
	}


	@Override
	public void addPopup(Container popup) {
		addChild(popup);
		
	}

}
