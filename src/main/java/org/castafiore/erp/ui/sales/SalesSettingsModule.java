package org.castafiore.erp.ui.sales;

import org.castafiore.erp.BusinessUnit;
import org.castafiore.erp.Currency;
import org.castafiore.erp.CurrencyMatrix;
import org.castafiore.erp.CustomerCategory;
import org.castafiore.erp.CustomerType;
import org.castafiore.erp.Employee;
import org.castafiore.erp.PaymentType;
import org.castafiore.erp.SalesOrderLineType;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.configs.ConfigAccordion;
import org.castafiore.erp.ui.form.controls.EXPortButton;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.ui.ex.form.button.EXButtonSet;

public class SalesSettingsModule extends AbstractEXModule{

	public SalesSettingsModule() {
		super("SalesSettings", "Customer Settings");
		
		//currency
		//Customer category
		//Customer type
		//Business Units
		
		GridFrame currency = new GridFrame(Currency.class);
		//currency.getMainFormSet().setDisplay(false);
		
		EXButtonSet exportSet = new EXButtonSet("export");
		exportSet.addClass("btn-group-xs");
		exportSet.addItem(new EXPortButton(currency));
		currency.addFormButtonSet(exportSet);
		
		GridFrame category = new GridFrame(CustomerCategory.class);
		GridFrame type = new GridFrame(CustomerType.class);
		GridFrame businessUnit = new GridFrame(BusinessUnit.class);
		GridFrame salesLineType = new GridFrame(SalesOrderLineType.class);
		
		
		GridFrame employee = new GridFrame(Employee.class);
		
		//GridFrame exchangeRates = new GridFrame(CurrencyMatrix.class);
		GridFrame paymentTypes = new GridFrame(PaymentType.class);
		
		ConfigAccordion acc = new ConfigAccordion("as");
		acc.addItem("Currency", "", currency);
		
		
		
		
		//acc.addItem("Exchange rates", "", exchangeRates);
		acc.addItem("Customer category", "", category);
		acc.addItem("Customer Order type", "", type);
		acc.addItem("Customer business unit", "", businessUnit);
		acc.addItem("Payment types", "", paymentTypes);
		acc.addItem("Line types", "", salesLineType);
		acc.addItem("Salesman", "", employee);
		addChild(acc, "0:0");
		
	}

}
