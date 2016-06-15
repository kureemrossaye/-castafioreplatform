package org.castafiore.portal.ui.data;

import java.util.List;

import org.springframework.context.MessageSource;

public abstract class SimpleDataLocator<T> extends DataLocator<T>{

	protected List<T> data = null;
	
	public SimpleDataLocator(Class<T> clazz, MessageSource messageSource) {
		super(clazz, messageSource);
	}
	
	public abstract List<T> loadAll();

	@Override
	public int getRowCount() {
		if(data == null){
			data = loadAll();
		}
		return data.size();
	}

	@Override
	public List<T> getPage(int page) {
		if(data == null){
			data = loadAll();
		}
		int fromIndex = page*getRowsPerPage();
		int toIndex = fromIndex + getRowsPerPage();
		if(toIndex > data.size()){
			toIndex = data.size();
		}
		return data.subList(fromIndex, toIndex);
	}
	
	@Override
	public void refresh() {
		data = null;
		
	}

}
