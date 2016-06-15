package org.castafiore.erp.ui.dashboard;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class SalesDashboard extends AbstractEXModule{

	public SalesDashboard() {
		super("SalesDashboard", "Sales Dashboard", "", new String[]{}, "12");
		
		String[] coords =new String[]{	"112.75,68,176.75,132",
										"128.75,536.501,196.75,604.501",
										"125.5,275.501,200,350.001",
										"472,177.501,536,241.501",
										"840,173.501,912,245.501",
										"130.75,411.251,194.75,475.251",
										"659.499,173.501,731.499,245.501",
										"308,177.501,372,241.501",
										"308,538.501,372,602.501",
										"472,538.501,536,602.501",
										"840,308.751,912,380.751",
										"659.499,308.751,731.499,380.751",
										"663.499,421.251,727.499,485.251",
										"663.499,538.501,727.499,602.501"
									};
		
		String[] names = new String[]{"customer","stockandwarehousing","manufacturingorder", "quotation", "salesorder","manufacturing","salesorder", "jobs", "delivery", "bank", "vat", "salesreturn", "statement", "statementcharges"};
		
		Container c = new EXContainer("ss", "div");
		Container map = new EXContainer("s", "map");
		for(int i =0; i < names.length;i++){
			Container area = new EXContainer(names[i], "area").setAttribute("shape", "rect").setAttribute("coords", coords[i]).setAttribute("href", "#" + names[i]);
			map.addChild(area);
			
		}
		
		Container img = new EXContainer("img", "img").setAttribute("src", "img/dashboard/SalesDashboard.png");
		c.addChild(map);
		c.addChild(img);
		
		addChild(c,"0:0");
		
		
		
		/**
		 * 	<area name="customer" shape="rect" coords="112.75,68,176.75,132" href="#customer" alt="Hyperlink1"/>
			<area name="stockandwarehousing" shape="rect" coords="128.75,536.501,196.75,604.501" href="#stockandwarehousing" alt="Hyperlink1" />
			<area name="manufacturingorder" shape="rect" coords="125.5,275.501,200,350.001" href="manufacturingorder" alt="Hyperlink1" />
			<area name="quotation" shape="rect" coords="472,177.501,536,241.501" href="#quotation" alt="Hyperlink1" />
			<area name="salesorder" shape="rect" coords="840,173.501,912,245.501" href="#salesorder" alt="Hyperlink1" />
			<area name="manufacturing" shape="rect" coords="130.75,411.251,194.75,475.251" href="#manufacturing" alt="Hyperlink1" />
			<area name="salesorder" shape="rect" coords="659.499,173.501,731.499,245.501" href="#salesorder" alt="Hyperlink1" />
			<area name="jobs" shape="rect" coords="308,177.501,372,241.501" href="#jobs" alt="Hyperlink1" />
			<area name="delivery" shape="rect" coords="308,538.501,372,602.501" href="#delivery" alt="Hyperlink1" />
			<area name="bank" shape="rect" coords="472,538.501,536,602.501" href="#bank" alt="Hyperlink1" />
			<area name="vat" shape="rect" coords="840,308.751,912,380.751" href="#vat" alt="Hyperlink1" />
			<area name="salesreturn" shape="rect" coords="659.499,308.751,731.499,380.751" href="#salesreturn" alt="Hyperlink1" />
			<area name="statement" shape="rect" coords="663.499,421.251,727.499,485.251" href="#statement" alt="Hyperlink1" />
			<area name="statementcharges" shape="rect" coords="663.499,538.501,727.499,602.501" href="#statementcharges" alt="Hyperlink1" />
		 */
		
		
		
		
		
	}

}
