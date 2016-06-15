package org.castafiore.erp.ui.form;

import java.math.BigDecimal;

import groovy.transform.Field;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.SalesOrderLine;
import org.castafiore.erp.ui.form.controls.InputControl;

public class TotalEvaluator implements Evaluator<BigDecimal>{

	

	@Override
	public void evaluate(InputControl<BigDecimal> input, Field prop, String field,
			Class<? extends BaseErpModel> vo, BaseErpModel model) {
		SalesOrder s = (SalesOrder)model;
		
		BigDecimal total = BigDecimal.ZERO;
		for(SalesOrderLine line : s.getLines()){
			if(!line.isNull()){
				total = total.add(line.getTotal());
			}
		}
		
		input.setValue(total);
		
	}

}
