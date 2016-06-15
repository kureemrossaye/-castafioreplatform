package org.castafiore.erp.utils;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


import org.castafiore.erp.Parameter;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ParameterUtil {
	private static Map<String, String> CACHE = new WeakHashMap<String, String>();
	
	private static GridController controller = new GridController();
	
	
	public static String getParam(String key, String defaultValue){
		
		if(CACHE.containsKey(key)){
			return CACHE.get(key);
		}
		
		Session session = SpringUtil.getBeanOfType(Dao.class).getSession();
		List params = session.createCriteria(Parameter.class).add(Restrictions.eq("key", key)).list();
		if(params.size() >0){
			Parameter parameter = ((Parameter)params.get(0));
			if(!StringUtil.isNotEmpty(parameter.getValue())){
				parameter.setValue(defaultValue);
				try{
				controller.updateRecord(parameter);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			CACHE.put(key, parameter.getValue());
			return parameter.getValue();
		}else{
			Parameter parameter = new Parameter();
			parameter.setKey(key);
			parameter.setValue(defaultValue);
			session.save(parameter);
			CACHE.put(key, parameter.getValue());
			return parameter.getValue();
		}
	}

}
