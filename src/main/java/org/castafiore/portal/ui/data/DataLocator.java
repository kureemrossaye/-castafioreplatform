package org.castafiore.portal.ui.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.castafiore.portal.annotations.Column;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.table.TableModel;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public abstract class DataLocator<T> implements TableModel {

	private Class<T> clazz_;

	private int rowsPerPage = 15;

	private List<Field> fields = new LinkedList<Field>();

	private MessageSource messageSource_;

	public DataLocator(Class<T> clazz, MessageSource messageSource) {
		super();
		this.clazz_ = clazz;
		this.messageSource_ = messageSource;
		init();
	}

	private void init() {
		final org.castafiore.portal.annotations.Table table = clazz_
				.getAnnotation(org.castafiore.portal.annotations.Table.class);

		if(table == null){
			throw new UIException("Missing annotation: The class " + clazz_.getName() + " miss the annotation @Table");
		}
		fields.clear();
		ReflectionUtils.doWithFields(clazz_, new FieldCallback() {

			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				Column col = field.getAnnotation(Column.class);
				if(col != null){
					if (col.tableId() == table.id()) {
						fields.add(col.position(), field);
	
					}
				}
			}
		});
		
		
		if(fields.size() == 0){
			throw new  UIException("Missing annotation: The class " + clazz_.getName() + " miss the annotation @Column on at least one of its field");
		}
	}

	protected String getLabel(Field field) {
		String key = "table." + clazz_.getSimpleName().toLowerCase() + "." + field.getName();
		try {
			return messageSource_.getMessage(key, null, Locale.ENGLISH);
		} catch (Exception e) {
			return "??" + field.getName() + "??";
		}
	}

	public abstract List<T> getPage(int page);

	public T getRow(int row, int page) {
		return getPage(page).get(row);
	}

	@Override
	public int getColumnCount() {
		return fields.size();
	}

	@Override
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	@Override
	public String getColumnNameAt(int index) {
		return getLabel(fields.get(index));
	}

	@Override
	public Object getValueAt(int col, int row, int page) {

		T oRow = getRow(row, page);
 
		Field f = fields.get(col);
		try {
			Method m= BeanUtils.getPropertyDescriptor(f.getDeclaringClass(), f.getName()).getReadMethod();
			return m.invoke(oRow);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Field f = fields.get(columnIndex);
		return f.getType();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	 
	public abstract void refresh();

}
