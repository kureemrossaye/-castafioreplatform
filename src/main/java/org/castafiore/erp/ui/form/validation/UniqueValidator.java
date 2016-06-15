package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;

import org.castafiore.erp.ui.form.Error;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class UniqueValidator implements Validator{

	@Override
	public String getName() {
		return "unique";
	}

	@Override
	public Error validate(Field field, Object value) {
		if(value != null){
			Class c = field.getDeclaringClass();
			String name = field.getName();
			Session session = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession();
			Integer count = Integer.parseInt(session.createCriteria(c).add(Restrictions.eq(name, value)).setProjection(Projections.count(name)).uniqueResult().toString());
			
			if(count > 0){
				Error error = new Error(field, value.toString() + " already exists. Please choose another one");
				return error;
			}
			
			
		}
		
		return null;
	}

}
