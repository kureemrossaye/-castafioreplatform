package org.castafiore.erp.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;

public class ReflectionUtils {
	
	public static Field findField(Class<?> clazz, String name) {
		if(name.contains(".")){
			String[] parts = StringUtil.split(name, ".");
			Class<?> start = clazz;
			Field result = null;
			for(int i=0;i < parts.length;i++){
				String field = parts[i];
				result =   org.springframework.util.ReflectionUtils.findField(start, field);
			}
			return result;
		}else{
		
			return org.springframework.util.ReflectionUtils.findField(clazz, name);
		}
	}
	
	 public static <A extends Annotation> A getAnnotation(Class<?> vo, Class<A> annotationClass) {
	       if(vo == null){
	    	   return null;
	       }
	       
	       String className = vo.getClass().getName();
			if(className.contains("_$$_")){
				className = className.substring(0,className.indexOf("_$$_"));
				////model =(BaseErpModel)SpringUtil.getBeanOfType(Dao.class).getSession().load(className,model.getId());
				try{
				vo = Class.forName(className);
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
	       
	       A result = vo.getAnnotation(annotationClass);
	       if(result == null){
	    	   return getAnnotation(vo.getSuperclass(), annotationClass);
	       }
	       
	       return result;

	       
	    }

}
