package org.castafiore.erp.ui.form.controls;

import java.math.BigInteger;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.ui.form.EmbededForm;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.NoInterceptor;
import org.castafiore.erp.ui.form.controls.search.DateSearchControl;
import org.castafiore.erp.ui.form.controls.search.DecimalSearchControl;
import org.castafiore.erp.ui.form.controls.search.IntegerSearchControl;
import org.castafiore.erp.ui.form.controls.search.LookupSearchControl;
import org.castafiore.erp.ui.form.controls.search.TextSearchControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.view.View;
import org.castafiore.erp.ui.view.ViewAssociation;
import org.castafiore.erp.ui.view.ViewBoolean;
import org.castafiore.erp.ui.view.ViewDate;
import org.castafiore.erp.ui.view.ViewDecimal;
import org.castafiore.erp.ui.view.ViewInt;
import org.castafiore.erp.ui.view.ViewTable;
import org.castafiore.erp.ui.view.ViewText;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.utils.StringUtil;

public class ControlsUtil {

	public static MagicSelectControl<BaseErpModel>  getMagicSelect(String field, Field prop){
		try{
			Class c = prop.lookupModel();
			BigInteger[] status = null;
			if(prop.status() != null && prop.status().length >0){
				status = new BigInteger[prop.status().length];
				for(int i = 0; i < prop.status().length;i++){
					status[i] = new BigInteger(prop.status()[i] + "");
				}
			}
			return new MagicSelectControl<BaseErpModel>(field, c, prop.dataLocator().newInstance(),status);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public static View getViewUI(String field, Class<? extends BaseErpModel> vo, GridController controller){
		FieldType type = ReflectionUtils.findField(vo, field).getAnnotation(Field.class).type();
		if(type == FieldType.alphaNumeric){
			return new ViewText(field, vo);
		}else if(type == FieldType.date || type==FieldType.datetime){
			return new ViewDate(field, vo);
		}else if(type == FieldType.email){
			return new ViewText(field, vo);
		}else if(type == FieldType.Enum || type==FieldType.select){
			return new ViewText(field, vo);
		}else if(type == FieldType.Float || type==FieldType.money){
			return new ViewDecimal(field, vo);
		}else if(type == FieldType.Int){
			return new ViewInt(field,vo);
		}else if(type == FieldType.lookup){
			return new ViewAssociation(field, vo,controller);
		}else if(type == FieldType.money){
			return new ViewDecimal(field, vo);
		}else if(type == FieldType.textArea){
			return new ViewText(field, vo);
		}else if(type == FieldType.Boolean){
			return new ViewBoolean(field, vo);
		}else if(type == FieldType.multiselect){
			return new ViewTable(field, vo, controller);
		}else if( type== FieldType.form){
			
			return new ViewAssociation(field, vo,controller);
			
		}else if( type== FieldType.table){
			
			return new ViewTable(field, vo, controller);
			
		}else{
			return new ViewText(field, vo);
		}
		
		
		
		
	}
	
	public static MultiSelectControl  getMultiSelect(String field, Field prop){
		try{
			Class c = prop.lookupModel();
			BigInteger[] status = null;
			if(prop.status() != null && prop.status().length >0){
				status = new BigInteger[prop.status().length];
				for(int i = 0; i < prop.status().length;i++){
					status[i] = new BigInteger(prop.status()[i] + "");
				}
			}
			return new MultiSelectControl(field, c, prop.dataLocator().newInstance(),status);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public static SimpleSelectControl<BaseErpModel> getSelect(String field, Field prop){
		Item[] items = prop.items();
		DefaultDataModel<KeyValuePair> model = new DefaultDataModel<KeyValuePair>();
		if(items != null && items.length > 0){
			for(Item item : items){
				model.addItem(new SimpleKeyValuePair(item.id(),item.text()));
			}
		}
		return new SimpleSelectControl(field, model);
	}

	public static void applyInterceptors(InputControl<?> uifield, IGroup group,Field prop){
		if(!prop.fieldInterceptor().equals(NoInterceptor.class)){
			try{
				prop.fieldInterceptor().newInstance().decorateField(uifield, group);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void applyValidators(InputControl<?> uifield, IGroup group,Field prop){
		
		if(prop.required()){
			group.addValidator(uifield, "required");
		}
		
		if(prop.max() != Integer.MIN_VALUE || prop.min() != Integer.MAX_VALUE){
			group.addValidator(uifield, "range");
		}
		
		
		for(String validator : prop.validators()){
			group.addValidator(uifield, validator);
		}
	}
	
	
	public static Container getSearchUI(String field,Field prop,   Class<? extends BaseErpModel> vo, GridFrame frame){
		FieldType type = prop.type();
		Container c = new EXContainer("", "div").addClass("search-form");
		if(type == FieldType.alphaNumeric){
			
			return new TextSearchControl(field, prop, vo,frame);
		}else if(type == FieldType.date || type== FieldType.datetime){
			return new DateSearchControl(field, frame);
		}else if(type == FieldType.email){
			return new TextSearchControl(field, prop, vo,frame);
		}else if(type == FieldType.Enum || type==FieldType.select){
			c.addChild(ControlsUtil.getSelect(field, prop));	
		}else if(type == FieldType.Float){
			return new DecimalSearchControl(field, frame);
		}else if(type == FieldType.Int){
			return new IntegerSearchControl(field, frame);
		}else if(type == FieldType.lookup){
			try{
				return new LookupSearchControl(field, prop, vo, frame);
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(type == FieldType.money){
			//c.addChild(new EXContainer("", "span").setText("From :")).addChild(new DecimalControl("from")).addChild(new EXContainer("", "span").setText("To :")).addChild(new DecimalControl("to"));
			return new DecimalSearchControl(field, frame);
		}else if(type == FieldType.textArea){
			return new TextSearchControl(field, prop, vo,frame);
		}else{
			return new TextSearchControl(field, prop, vo,frame);
		}
		
		
		return c;
	}
	
	public static InputControl<?> getField(String field,Field prop, IGroup group, GridController controller_){
		FieldType type = prop.type();
		InputControl<?> uifield ;
		if(type == FieldType.alphaNumeric){
			uifield = new TextControl(field);
		}else if(type == FieldType.date){
			uifield = new DateControl(field);
		}else if(type == FieldType.datetime){
			
			uifield = new DateControl(field);
			((DateControl)uifield).setShowTime(true);
		}else if(type == FieldType.email){
			uifield = new TextControl(field);
		}else if(type == FieldType.Enum || type==FieldType.select){
			uifield = ControlsUtil.getSelect(field, prop);	
		}else if(type == FieldType.Float){
			uifield = new DecimalControl(field);
		}else if(type == FieldType.Int){
			uifield = new IntegerControl(field);
		}else if(type == FieldType.lookup){
			uifield = ControlsUtil.getMagicSelect(field, prop);
		}else if(type == FieldType.money){
			uifield = new CurrencyControl(field);
		}else if(type == FieldType.textArea){
			uifield = new TextAreaControl(field);
		}else if(type == FieldType.Boolean){
			uifield =new CheckBoxControl(field); //  new TextAreaControl(field);
		}else if(type == FieldType.multiselect){
			uifield =getMultiSelect(field, prop); //  new TextAreaControl(field);
		}else if( type== FieldType.form){
			
			Class c = prop.lookupModel();
			
			uifield = new EmbededForm<BaseErpModel>(field, c, controller_);
			
		}else{
			uifield = new TextControl(field);
		}
		
		if(StringUtil.isNotEmpty(prop.style())){
			uifield.setAttribute("style", prop.style());
		}
		
		if(prop.size() >0){
			uifield.setAttribute("size", prop.size() + "");
		}
		
		if(prop.maxLength() > 0){
			uifield.setAttribute("maxlength", prop.maxLength() + "");
		}
		
		
		
		if((uifield instanceof TextControl) && StringUtil.isNotEmpty(prop.format())){
			((TextControl)uifield).applyMask(prop.format());
		}
		
		if((uifield instanceof TextControl) && StringUtil.isNotEmpty(prop.format())){
			((TextControl)uifield).applyMask(prop.format());
		}
		
		
		return uifield;
	}

}