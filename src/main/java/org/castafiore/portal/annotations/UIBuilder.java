package org.castafiore.portal.annotations;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.castafiore.portal.ui.data.EXDataForm;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.widgets.EXWizardWidget;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

@Component
public class UIBuilder {
	
	
	@Autowired
	MessageSource messageSource;
	
	public <T> EXWizardWidget createWizard(Class<T> clazz){
		Wizard wizard = clazz.getAnnotation(Wizard.class);
		Form[] forms = wizard.forms();
		EXWizardWidget wiz = new EXWizardWidget(clazz.getName() + "Wizard", wizard.title());
		for(Form form : forms){
			EXDataForm<T> widget = createForm(form, clazz);
			wiz.addStep(form.label(), widget);
		}
		return wiz;
	}
	
	
	public <T> EXDataForm<T> createForm(Form form, Class<T> clazz){
		EXDataForm<T> formWidget = new EXDataForm<T>(form.id() +"",form.label()){

			@Override
			public void setModel(T model) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public T getModel() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void validate() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setDataGrid(EXDataGrid<T> grid) {
				// TODO Auto-generated method stub
				
			}
			
		};
		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				org.castafiore.portal.annotations.Field f = field.getAnnotation(org.castafiore.portal.annotations.Field.class);
				if(f.formId() == form.id()){
					StatefullComponent<?> component = createField(f, field);
					String formName = form.name();
					String key = clazz.getSimpleName().toLowerCase() ;
					if(!formName.equals("")){
						key = formName;
					}
					key = key  + "." + component.getName();
					String label = messageSource.getMessage(key, (Object[])null,Locale.US);
					formWidget.addField(label, component);
				}
			}
		});
		return formWidget;
	}
	
	
	public StatefullComponent<?> createField(org.castafiore.portal.annotations.Field f, Field field){
		StatefullComponent<?> component = null;
		
		switch (f.type()) {
		case text:
			component = new EXInput(field.getName());
			break;
		case password:
			component = new EXPassword(field.getName());
			break;
		case datepicker:
			component = new EXDatePicker(field.getName());
			break;
		
		case textarea:
			component = new EXTextArea(field.getName());
			break;
			
		case checbox:
			component = new EXCheckBox(field.getName());
			break;
			
		case phone:
			component = new EXInput(field.getName());
			break;
		
		case creditcard:
			component = new EXInput(field.getName());
			break;
			
		case time:
			component = new EXTimePicker(field.getName());
			break;
			
		default:
			component = new EXInput(field.getName());
			break;
		}
		
		return component;
	}

	public Table createTable(Class<?> clazz) {
		final org.castafiore.portal.annotations.Table table = clazz
				.getAnnotation(org.castafiore.portal.annotations.Table.class);

		List<Field> fields = new LinkedList<Field>();
		ReflectionUtils.doWithFields(clazz, new FieldCallback() {

			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Column col = field.getAnnotation(Column.class);
				if (col.tableId() == table.id()) {
					fields.add(field);
				}
			}
		});

		TableModel model = new TableModel() {

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}

			@Override
			public Object getValueAt(int col, int row, int page) {
				return null;
			}

			@Override
			public int getRowsPerPage() {
				return 15;
			}

			@Override
			public int getRowCount() {
				return 0;
			}

			@Override
			public String getColumnNameAt(int index) {
				return messageSource.getMessage(table.name() + "." + fields.get(index).getName(), null, Locale.US);
			}

			@Override
			public int getColumnCount() {
				return fields.size();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return fields.get(columnIndex).getType();
			}
		};
		
		EXTable table_ = new EXTable(table.name(), model);
		return table_;

	}

}
