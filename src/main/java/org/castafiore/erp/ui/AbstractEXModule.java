package org.castafiore.erp.ui;

import org.castafiore.ui.ex.layout.EXMigLayout;
/**
 * Sample layout configuration
 * 
 * 12;4:4:4;3:3:6
 * 
 * 
 * 
 * @author acer
 *
 */
public  class AbstractEXModule extends EXMigLayout {
	
	private String label;
	
	private String icon;
	
	private String[] path;

	
	
	public AbstractEXModule(String name, String label,
			String icon, String[] path, String layout) {
		super(name, layout);
		this.label = label;
		this.icon = icon;
		this.path = path;
		addClass("AbstractEXModule");
		
	}
	
	

	public AbstractEXModule(String name, String label) 
	{
		this(name,label, "", new String[]{}, "12");
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public String[] getPath() {
		return path;
	}



	public void setPath(String[] path) {
		this.path = path;
	}


	

	
	
	

}
