package org.castafiore.erp.ui.grid;

import java.util.Map;


public interface DataController {
	
	 /**
	   * Method called when used has clicked on reload/cancel button.
	   */
	  public void reload();


	  /**
	   * Method called when used has clicked on insert button.
	   */
	  public void insert();


	  /**
	   * Method called when used has clicked on copy button.
	   */
	  public void copy();


	  /**
	   * Method called when used has clicked on edit button.
	   */
	  public void edit();


	  /**
	   * Method called when used has clicked on delete button.
	   */
	  public void delete();


	  /**
	   * Method called when used has clicked on save button.
	   */
	  public boolean save();


	  /**
	   * Method called when used has clicked on export button.
	   */
	  public void export();
	  
	  /**
	   * Method called when used has clicked on import button.
	   */
	  public void importData();


	 

}
