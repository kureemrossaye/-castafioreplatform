package org.castafiore.erp;

import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.castafiore.utils.StringUtil;
import org.neo4j.ogm.annotation.NodeEntity;


/**
 * Represents the type of customer.<br>
 * A type can be  Cash Customer, Credit Customer, COD.<br>
 * It can actually be anything.
 * @author acer
 *
 */
@NodeEntity
@Table(columns={"code","title"})
@Forms(label="Main",name="Main",forms={
		@Form(layout="12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"code","title"})
		})
})
public class CustomerType extends BaseErpModel implements IAssociationModel{
	
	@org.castafiore.erp.annotations.Column(caption="Code",style="width:15%")
	@Field(caption="Code", required=true,size=8,maxLength=8, validators={"unique"},updateable=false,actions=CodeFieldAction.class, actionScope=ActionScope.create)
	private String code;
	
	@org.castafiore.erp.annotations.Column(caption="Title",style="width:85%")
	@Field(caption="Title",required=true)
	private String title;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return  code + " - " + title;
	}

	@Override
	public Boolean isNull() {
		return !StringUtil.isNotEmpty(code);
	}
	
	
	

}
