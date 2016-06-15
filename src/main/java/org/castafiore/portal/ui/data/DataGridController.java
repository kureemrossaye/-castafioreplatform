package org.castafiore.portal.ui.data;

public interface DataGridController<T> {
	
	public void insertRecord(T record);
	
	public void deleteRecord(T record);
	
	public void updateRecord(T record);
	
	

}
