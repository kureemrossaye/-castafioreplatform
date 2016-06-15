package org.castafiore.erp.ui.form.validation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.castafiore.erp.ui.form.Error;

public class RangeValidator implements Validator{

	@Override
	public String getName() {
		return "range";
	}

	@Override
	public Error validate(Field field, Object value) {
		int min = field.getAnnotation(org.castafiore.erp.annotations.Field.class).min();
		int max = field.getAnnotation(org.castafiore.erp.annotations.Field.class).max();
		boolean minok = false;
		boolean maxok = false;
		Integer val = null;
		if(value instanceof BigInteger){
			val = ((BigInteger)value).intValue();
		}else if(value instanceof BigDecimal){
			val = ((BigDecimal)value).intValue();
		}
		if(val != null){
			if(min == Integer.MAX_VALUE ||  val >= min){
				minok = true;
			}
			
			if(max == Integer.MIN_VALUE || val <= max){
				maxok = true;
			}
		}
		
		if(!minok || !maxok){
			String msg = "The field {caption} should be ";
			if(maxok && !minok){
				msg = msg + " greater or equal to " + min;
			}else if(minok && !maxok){
				
				msg = msg + "less or equal to " + max; 
			}else{
				msg = msg + " between " + min + " and " + max + " inclusive";
			}
			return new Error(field, msg);
		}
		
		return null;
	}
	
	

}
