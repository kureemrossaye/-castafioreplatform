package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.ui.form.controls.ControlsUtil;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.erp.workflow.Revision;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.layout.EXMigLayout;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class ViewFrame extends EXContainer implements TabModel {

	private Class<? extends BaseErpModel> vo;

	
	private EXTabPanel panel;
	
	private List<ViewForm> forms = new ArrayList<ViewFrame.ViewForm>();
	
	private BaseErpModel data_;
	
	private List<Integer> aiForms_ = new ArrayList<Integer>();
	
	private GridController controller_;
	
	private Revision revision ;
	

	public ViewFrame(Class<? extends BaseErpModel> vo, GridController controller) {
		super(vo.getSimpleName(), "div");
		this.controller_ = controller;
		int count=0;
		for(Form f : ReflectionUtils.getAnnotation(	vo, Forms.class).forms()){
			boolean has = false;
			for(Group g : f.groups()){
				if(g.impl().equals(org.castafiore.erp.ui.form.Group.class)){
					has = true;
					break;
				}
			}
			
			if(has){
				aiForms_.add(count);
			}
			count++;
		}
		
		this.vo = vo;
		panel =  new EXTabPanel("panel", this);
		if(aiForms_.size() == 1){
			panel.setShowTabs(false);
		}
		//setBody(panel);
		addChild(panel);
		
		
	}
	
	public void setData(Revision data){
		
		this.revision = data;
		
		//new GridController().activate(data);
		data=SpringUtil.getBeanOfType(WorkflowManager.class).activate(data);
		this.data_ = data.getInstance();
		for(ViewForm f : forms){
			f.setData(data_);
		}
	}

	public void setData(BaseErpModel data){
		revision = null;
		controller_.activate(data);
		this.data_ = data;
		for(ViewForm f : forms){
			f.setData(data);
		}
	}



	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		Forms forms = ReflectionUtils.getAnnotation(	vo, Forms.class);
		return forms.forms()[aiForms_.get(index)].label();
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		ViewForm form = new ViewForm(ReflectionUtils.getAnnotation(	vo, Forms.class).forms()[aiForms_.get(index)]);
		forms.add(form);
		if(data_ != null){
			if(revision != null){
				revision = SpringUtil.getBeanOfType(WorkflowManager.class).activate(revision);
				data_ = revision.getInstance();
			}else{
				controller_.activate(data_);
			}
			form.setData(data_);
		}
		return form;
	}

	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public int size() {
		return aiForms_.size();
	}
	
	public class ViewForm extends EXMigLayout{
		
		private Form form;
		
		private List<ViewGroup> groups = new ArrayList<ViewFrame.ViewGroup>();
		public ViewForm(Form form) {
			super(form.name(), form.layout());
			this.form = form;
			init();
		}
		
		public void init(){
			for(Group g : form.groups()){
				ViewGroup group = new ViewGroup(g);
				groups.add(group);
				addChild(group, g.layoutData());
			}
		}
		
		public void setData(BaseErpModel data){
			
			for(ViewGroup g : groups){
				g.setData(data);
			}
		}

	}
	
	public class ViewGroup extends EXGrid{
		private Group group;

		private List<View> views = new ArrayList<View>();
		public ViewGroup(Group group) {
			super(group.name(),2,group.fields().length);
			this.group = group;
			setStyle("width", "99%");
			init();
		}
		
		public void init(){
			int count =0;
			for(String field : group.fields()){
				Field f = ReflectionUtils.findField(vo, field);
				org.castafiore.erp.annotations.Field prop = f.getAnnotation(org.castafiore.erp.annotations.Field.class);
				String label = prop.caption();
				View viewer = ControlsUtil.getViewUI(field, vo, controller_);
				views.add(viewer);
				Container tdLabel = getCell(0, count);
				Container tdField = getCell(1, count);
				tdLabel.setStyle("text-align", "right").setStyle("padding-right", "12px");
				if(viewer instanceof ViewTable){
					tdLabel.remove();
					tdField.setAttribute("colspan", "2");
					viewer.setStyle("width", "100%");
					addInCell(0, count, viewer);
				}else{
					addInCell(0,count, new EXContainer("label", "label").setText(label));
					addInCell(1, count, viewer);
				}
				count++;
			}
			
		}
		
		public void setData(BaseErpModel data){
			for(View view : views){
				view.setData(data);
			}
		}
		
		
	}

}
