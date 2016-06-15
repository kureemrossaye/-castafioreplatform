package org.castafiore.erp.inventory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.controls.DecimalControl;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.TextControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.EXMigLayout;

public class ItemInfoGroup extends EXMigLayout implements IGroup{

	private DecimalControl realStock =  new DecimalControl("realStock");
	
	private DecimalControl onOrder =  new DecimalControl("onOrder");
	
	private DecimalControl onSales =  new DecimalControl("onSales");
	
	private DecimalControl stockInHand =  new DecimalControl("stockInHand");
	
	public void addValidator(InputControl<?> input, String validator){
	}
	

	public ItemInfoGroup(org.castafiore.erp.annotations.Group group, Class<? extends BaseErpModel> vo_, GridController controller) {
		super(group.name(), "fieldset");
		
		
	
			Container uititle = new EXContainer("title", "legend").setStyle("padding", "3px").setStyle("font-weight", "bold").setStyle("color", "#777");
			addChild(uititle.setText(group.label()));
			uititle.setDisplay(group.showTitle());
			
			Container cont = new EXContainer("cont", "div").addClass("w2ui-group");
			addChild(cont);
			
			
			
			Container lblstockInHand = new EXContainer("label", "div").addClass("w2ui-label").setText("Stock on Hand");
			Container uifieldcont1 = new EXContainer("field", "div").addClass("w2ui-field").addChild(stockInHand.setStyle("max-width", "100px").setAttribute("disabled", "true"));
			cont.addChild(lblstockInHand);
			cont.addChild(uifieldcont1);
			
			
			
			
			
			Container lblonSales = new EXContainer("label", "div").addClass("w2ui-label").setText("Qty on Sales Order");
			Container uifieldcont2 = new EXContainer("field", "div").addClass("w2ui-field").addChild(onSales.setStyle("max-width", "100px").setAttribute("disabled", "true"));
			cont.addChild(lblonSales);
			cont.addChild(uifieldcont2);
			
			
			
			Container lblonOrder = new EXContainer("label", "div").addClass("w2ui-label").setText("Qty on Purchase Order");
			Container uifieldcont3 = new EXContainer("field", "div").addClass("w2ui-field").addChild(onOrder.setStyle("max-width", "100px").setAttribute("disabled", "true"));
			cont.addChild(lblonOrder);
			cont.addChild(uifieldcont3);
			
			
			
			
			Container lblrealStock = new EXContainer("label", "div").addClass("w2ui-label").setText("Real Stock");
			Container uifieldcont4 = new EXContainer("field", "div").addClass("w2ui-field").addChild(realStock.setStyle("max-width", "100px").setAttribute("disabled", "true"));
			cont.addChild(lblrealStock);
			cont.addChild(uifieldcont4);
			
			
		
		
	}
	
	
	public Map<InputControl<?>, List<String>> getValidators(){
		return new HashMap<InputControl<?>, List<String>>(0);
	}
	
	public void fillModel(final BaseErpModel model){
		
	}
	public void setData(final BaseErpModel model){
			
		realStock.setValue(BigDecimal.ZERO);
		onOrder.setValue(BigDecimal.ZERO);
		onSales.setValue(BigDecimal.ZERO);
		stockInHand.setValue(BigDecimal.ZERO);
		
	}
	
	public Container getLabelContainerFor(InputControl<?> field){
		Container fieldcont = field.getParent();
		Container fieldset = fieldcont.getParent();
		Container label = fieldset.getChildByIndex( fieldset.getChildren().indexOf(fieldcont)-1);
		return label;
	}


	@Override
	public void setEnabled(boolean b) {
		
		
	}


	@Override
	public InputControl<?> getField(String name) {
		return null;
	}


	

}
