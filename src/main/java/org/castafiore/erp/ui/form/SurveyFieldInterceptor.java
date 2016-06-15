package org.castafiore.erp.ui.form;

import java.util.List;
import java.util.Map;

import org.castafiore.elieandsons.SalesClientService;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.sales.IContractDetail;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.StringUtil;

public class SurveyFieldInterceptor implements FieldDecorator , Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IGroup form_ = null;
	
	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		form_ = form;
		field.addEvent(this, READY);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		//container.mask().server(this);
		container.invoke("on", "autocompleteselect", container.clone().server(this));
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String fsCode = form_.getField("fsCode").getValue().toString();
		
		if(StringUtil.isNotEmpty(fsCode)){
			List<IContractDetail> detail = new SalesClientService().searchOrders(fsCode, 0, 1);
			if(detail.size() > 0){
				IContractDetail result = detail.get(0);
				Object plandetail = result.getPlanDetail();
				InputControl planfield = form_.getField("plan");
				planfield.setValue(plandetail);
				InputControl name = form_.getField("name");
				name.setValue(result.getContactFirstName() + " " + result.getContactLastName());
				
				
				InputControl phone = form_.getField("phone");
				phone.setValue(result.getContactTel() + " " + result.getContactMobile());
			}
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
