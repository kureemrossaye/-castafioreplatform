package org.castafiore.portal.ui.data;

import org.castafiore.ui.Container;

public interface DataForm<T> extends Container {

	public void setModel(T model);

	public T getModel();

	public void validate();
	
	public void reset();
	
	public void setDataGrid(EXDataGrid<T> grid);

}
