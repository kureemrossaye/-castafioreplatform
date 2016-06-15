package org.castafiore.portal.ui.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.castafiore.portal.annotations.Form;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public class EXEntityDataForm<T> extends EXDataForm<T> {

	protected Class<?> clazz_;

	protected MessageSource messageSource_;

	//private T model_;

	private List<Field> fields = new LinkedList<Field>();

	public EXEntityDataForm(String name, Class<T> clazz, MessageSource messageSource) {
		super(name, "");
		this.clazz_ = clazz;
		this.messageSource_ = messageSource;
		init();
	}

	protected void init() {

		Form form = clazz_.getAnnotation(Form.class);
		if (form != null) {
			String label = form.label();
			if (StringUtil.isNotEmpty(label)) {
				setTitle(label);
			} else {
				String formName = form.name();
				if (!StringUtil.isNotEmpty(formName)) {
					formName = clazz_.getSimpleName().toLowerCase();
				}
				String key = "form." + formName + ".title";
				setTitle(messageSource_.getMessage(key, null, Locale.US));
			}

			ReflectionUtils.doWithFields(clazz_, new FieldCallback() {

				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					org.castafiore.portal.annotations.Field f = field
							.getAnnotation(org.castafiore.portal.annotations.Field.class);
					
					if (f!= null && f.formId() == form.id()) {
						fields.add(field);
					}
				}
			});
		} else {
			throw new UIException("Missing annotation: Form required on class " + clazz_.getName());
		}

		for (Field f : fields) {
			addField(f);
		}

	}

	public EXDataForm<T> addField(Field field) {
		StatefullComponent<?> component = createField(
				field.getAnnotation(org.castafiore.portal.annotations.Field.class), field);
		String formName = clazz_.getAnnotation(Form.class).name();
		String key = clazz_.getSimpleName().toLowerCase();
		if (!formName.equals("")) {
			key = formName;
		}
		key = key + "." + component.getName();
		String label = messageSource_.getMessage(key, (Object[]) null, Locale.US);
		return addField(label, component);

	}

	protected StatefullComponent<?> createField(org.castafiore.portal.annotations.Field f, Field field) {
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

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(T model) {
		//this.model_ = model;
		for (Field f : fields) {
			try {
				Method m = BeanUtils.getPropertyDescriptor(f.getDeclaringClass(), f.getName()).getReadMethod();
				Object o = m.invoke(model);
				getField(f.getName()).setValue(o);
			} catch (Exception e) {
				throw new UIException(e);
			}

		}
	}

	@Override
	public T getModel() {
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void reset() {
		for (Field f : fields) {
			getField(f.getName()).setValue(null);

		}
	}

}
