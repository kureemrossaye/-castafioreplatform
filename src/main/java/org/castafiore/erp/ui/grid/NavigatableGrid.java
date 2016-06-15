package org.castafiore.erp.ui.grid;

import org.castafiore.ui.Container;

public interface NavigatableGrid extends Container{
	
	public Container getNextTd(int col, int row);

}
