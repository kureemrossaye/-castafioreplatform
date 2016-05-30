package org.castafiore.portal.ui.widgets.grid;

public interface GridController<T> {
	
	public void insertRecord(T record);
	
	public void deleteRecord(Long id);
	
	public void updateRecord(T record);
	
	

}
