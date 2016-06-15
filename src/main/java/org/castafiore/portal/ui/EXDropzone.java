package org.castafiore.portal.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.resource.FileData;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXDropzone extends EXContainer{

	public EXDropzone(String name) {
		super(name, "div");
	}
	
	private List<AfterUploadListener> listeners = new ArrayList<EXDropzone.AfterUploadListener>();
	private List<FileData> items = new ArrayList<FileData>();
	public FileData getFile() {
		if(items.size() == 0)
			return null;
		return items.get(0);
	}
	
	
	public void setFile(FileData item) {
		items.clear();
		this.items.add(item);
	}
	
	public void addFile(FileData item) {
		this.items.add(item);
	}
	
	public void fireCompleted(){
		for(AfterUploadListener listener : listeners){
			listener.afterUpload(this);
		}
	}
	public List<FileData>  getValue() {
			return items;
	}
	
	
	public void setValue(List<FileData> value) {

		items = value;
		
	}
	
	public void addAfterUploadListener(AfterUploadListener listener){
		this.listeners.add(listener);
		
	}
	
	
	public interface AfterUploadListener{
		public void afterUpload(EXDropzone field);
	}
	
	
	public void onReady(JQuery qu){
		String portal = getRoot().getName();
		qu.invoke("dropzone", new JMap().put("url", "castafiore/ui/?casta_applicationid="+portal+"&casta_componentid=" + getId()));
	}
	

}
