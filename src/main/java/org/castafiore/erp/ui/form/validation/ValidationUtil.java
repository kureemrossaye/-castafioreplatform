package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class ValidationUtil {
	
	public final static RequiredValidator REQUIRED = new RequiredValidator();
	
	public static boolean checkUnique(String code,Field field,Class<? extends BaseErpModel> model){
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(model).add(Restrictions.eq(field.getName(), code)).setProjection(Projections.count(field.getName())).list();
		if(l.size() == 0){
			return true;
		}
 		int count =  Integer.parseInt(l.get(0).toString());
 		return count == 0;
	}
	
	public static String getErrorMsg(Field field, String defaultErrorMsg){
		org.castafiore.erp.annotations.Field ann= field.getAnnotation(org.castafiore.erp.annotations.Field.class);
		if(ann != null){
			if(StringUtil.isNotEmpty(ann.errorMsg())){
				return ann.errorMsg();
			}
		}
		
		String caption = ann==null?field.getName(): ann.caption();
		
		return defaultErrorMsg.replace("{caption}", caption);
	}
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	
	
	public static Validator getValidator(String validator){
		try{
			Validator oval = SpringUtil.getApplicationContext().getBean(validator, Validator.class);
			if(oval == null){
				throw new UIException("validator:" + validator + " not configured in context. Configure an instance of a " + Validator.class.getName() + " with method getName() = " + validator);
			}
			return oval;
		}catch(NoSuchBeanDefinitionException nsde){
			throw new UIException("validator:" + validator + " not configured in context. Configure an instance of a " + Validator.class.getName() + " with method getName() = " + validator);
		}catch (BeanNotOfRequiredTypeException nfss){
			throw new UIException("validator:" + validator + " not configured in context. Configure an instance of a " + Validator.class.getName() + " with method getName() = " + validator,nfss);
		}catch(BeansException bs){
			throw new UIException("validator:" + validator + " not configured in context. Configure an instance of a " + Validator.class.getName() + " with method getName() = " + validator,bs);
		}
	}


}
