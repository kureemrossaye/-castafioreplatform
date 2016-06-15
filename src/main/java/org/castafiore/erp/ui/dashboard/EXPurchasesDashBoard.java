package org.castafiore.erp.ui.dashboard;

import java.util.Map;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.ERP;
import org.castafiore.erp.ui.purchases.PurchasesInvoiceModule;
import org.castafiore.erp.ui.purchases.PurchasesOrderModule;
import org.castafiore.erp.ui.purchases.PurchasesReturnModule;
import org.castafiore.erp.ui.purchases.PurchasesSettingsModule;
import org.castafiore.erp.ui.referential.EXItemsModule;
import org.castafiore.erp.ui.referential.EXSupplierModule;
import org.castafiore.erp.ui.vat.VatModule;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXPurchasesDashBoard extends AbstractEXModule implements Event{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


private final static String[] NAMES = new String[]{"Suppliers", "PurchaseOrders","PurchaseInvoices", "VAT", "PurchaseReturns", "StockAndWarehousing", "Settings"};
	
	
	private final static Class<?>[] CLAZZ={EXSupplierModule.class, PurchasesOrderModule.class, PurchasesInvoiceModule.class,  VatModule.class,PurchasesReturnModule.class, EXItemsModule.class,  PurchasesSettingsModule.class};

	public EXPurchasesDashBoard() {
		super("Purchases dashboard", "Purchases flowchart");
		init();
	}
	
	public void init(){
		EXXHTMLFragment fragment = new EXXHTMLFragment("fff", "templates/PurchasesDashBoard.xhtml");
		addChild(fragment);
		
		for(int i =0; i < NAMES.length;i++ ){
			String name = NAMES[i];
			Container img = new EXContainer(name, "img").setAttribute("src", "Purchases_files/"+name+".png");
			img.addEvent(this, CLICK);
			img.addClass("grow-rotate");
			img.setAttribute("module", CLAZZ[i].getName());
			fragment.addChild(img);
		}
	}
	
	
	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@SuppressWarnings("unchecked")
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
				@SuppressWarnings("rawtypes")
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
