package org.castafiore.erp.ui.dashboard;

import java.util.Map;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.ERP;
import org.castafiore.erp.ui.referential.EXBankModule;
import org.castafiore.erp.ui.referential.EXCustomerModule;
import org.castafiore.erp.ui.referential.EXItemsModule;
import org.castafiore.erp.ui.sales.QuotationsModule;
import org.castafiore.erp.ui.sales.SalesInvoiceModule;
import org.castafiore.erp.ui.sales.SalesOrderModule;
import org.castafiore.erp.ui.sales.SalesReturnModule;
import org.castafiore.erp.ui.sales.SalesSettingsModule;
import org.castafiore.erp.ui.vat.VatModule;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXSalesDashBoard extends AbstractEXModule implements Event{
	
	
	private final static String[] NAMES = new String[]{"Customers", "Quotes","SalesOrders","SalesInvoices", "Vat", "SalesReturns", "StockAndWarehousing", "Bank", "Settings"};
	
	
	private final static Class[] CLAZZ={EXCustomerModule.class, QuotationsModule.class, SalesOrderModule.class, SalesInvoiceModule.class, VatModule.class,SalesReturnModule.class, EXItemsModule.class, EXBankModule.class, SalesSettingsModule.class};

	public EXSalesDashBoard() {
		super("Sales dashboard", "Sales flowchart");
		init();
	}
	
	private void init(){
		EXXHTMLFragment fragment = new EXXHTMLFragment("fa", "templates/SalesDashBoard.xhtml");
		
		
		for(int i =0; i < NAMES.length;i++ ){
			String name = NAMES[i];
			Container img = new EXContainer(name, "img").setAttribute("src", "Sales_files/"+name+".png");
			img.addEvent(this, CLICK);
			img.addClass("grow-rotate");
			img.setAttribute("module", CLAZZ[i].getName());
			fragment.addChild(img);
		}
		addChild(fragment, "0:0");
	}
	
	
	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		try{
			
			if( ((EXWebServletAwareApplication)getRoot()).getRequest().getSession().getAttribute("credential") == null){
				ERP app = getAncestorOfType(ERP.class);
				app.getChildren().clear();
				app.setRendered(false);
				//getAncestorOfType(ERP.class).setRendered(false);
				app.addChild(new EXContainer("", "h1").setText("Please log before accessing this application"));
				app.addChild(new EXContainer("", "a").setAttribute("href", "logout.jsp").setText("Please click here to log in"));
			}else{
				Class c = Class.forName(container.getAttribute("module"));
			
				getAncestorOfType(ERP.class).getModuleContainer().setCurrentModule(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
