package org.castafiore.erp.ui.form.controls;

import org.castafiore.ui.ex.form.AbstractStatefullComponent;

public class ImageControl extends AbstractStatefullComponent<String>{

	public ImageControl(String name) {
		super(name, "img");
		setValue("");
		setAttribute("data-src", "holder.js/140x140");
	}

	@Override
	public String getValue() {
		return getAttribute("value");
	}

	@Override
	public void setValue(String value) {
		setAttribute("value", value);
		setAttribute("src", value);
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(!enabled){
			addClass("disabled");
		}else{
			removeClass("disabled");
		}
	}

}
