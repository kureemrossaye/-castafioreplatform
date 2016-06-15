package org.castafiore.portal.ui.portlets;

import java.util.Map;

import org.castafiore.portal.ui.EXDropzone;
import org.castafiore.portal.ui.EXDropzone.AfterUploadListener;
import org.castafiore.portal.ui.editors.EXTextEditor;
import org.castafiore.resource.FileData;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.IconImageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("mediaManager")
@Scope("prototype")
public class EXMediaManager extends EXXHTMLFragment implements Event {

	// private EXUpload upload = new EXUpload("myUpload");

	@Autowired
	private IconImageProvider iconProvider;

	Container listFile = new EXContainer("listFile", "ul").addClass("listfile");

	Container types = new EXContainer("types", "ul");

	Container menuRight = new EXContainer("menuRight", "ul").addClass("menuright");

	private File selectedFile = null;
	
	private EXInput searchInput = new EXInput("searchInput");
	
	

	private Container previous = new EXContainer("previous", "a").addClass("btn").addClass("prev")
			.setText("<span class=\"icon-chevron-left\"></span>");

	private Container next = new EXContainer("next", "a").addClass("btn").addClass("next")
			.setText("<span class=\"icon-chevron-right\"></span>");

	private Container selectAll = new EXContainer("selectAll", "a").addClass("btn").addClass("selectall")
			.setText("<span class=\"icon-check\"></span>Select All");

	private Container addNewFolder = new EXContainer("addNewFolder", "a").addClass("btn").addClass("newfolder")
			.setText("<span class=\"icon-folder-open\"></span>").setAttribute("title", "Add New Folder");

	private Container edit = new EXContainer("edit", "a").addClass("btn").addClass("newfolder")
			.setAttribute("title", "Create New").setText("<span class=\"icon-folder-open\"></span>");

	private Container delete = new EXContainer("delete", "a").addClass("btn").addClass("trash")
			.setAttribute("title", "Delete Selected Items").setText("<span class=\"icon-trash\"></span>");

	private Container addNew = new EXContainer("addNew", "a").addClass("btn").addClass("btn-primary")
			.setText("Add New Item");

	private final static String[] typeLabel = new String[] { "All", "Images", "Video", "Audio", "Documents" };

	private final static String[] defaultDrives = new String[] { "All Files", "Deleted Files", "Recently Added",
			"Recently Viewed", "New Folder" };
	
	private EXTextEditor editor = new EXTextEditor("editor");

	@Autowired
	private RepositoryService respositoryService;

	private int pageSize = 20;

	private int currentPage = 0;
	
	private EXDropzone dropzone = new EXDropzone("dropzone");

	@Autowired
	public EXMediaManager(RepositoryService repositoryService, IconImageProvider provider) {
		super("EXMediaManager", "templates/EXMediaManager.xhtml");
		this.respositoryService = repositoryService;
		this.iconProvider = provider;
		addClass("mediamgr");
		addChild(listFile);
		addChild(editor);
		
		addChild(dropzone.addClass("menuright").setStyle("height", "200px"));
		
		AfterUploadListener l = new AfterUploadListener() {
			
			@Override
			public void afterUpload(EXDropzone field) {
				FileData data = field.getFile();
				File f = new File();
				f.setName(data.getName());
				f.setMimeType(data.getMimeType());
				f.setTitle(data.getName());
				try{
				f.write(data.getInputStream());
				addFile(f);
				refreshList();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		
		dropzone.addAfterUploadListener(l);

		// addChild(upload);
		addChild(menuRight);

		addChild(types);
		addChild(searchInput.setStyleClass("filekeyword").setAttribute("placeholder", "Search file here"));

		addChild(previous.addEvent(this, CLICK)).addChild(next.addEvent(this, CLICK))
				.addChild(edit.addEvent(this, CLICK)).addChild(delete.addEvent(this, CLICK)).addChild(selectAll)
				.addChild(addNew.addEvent(this, CLICK)).addChild(addNewFolder.addEvent(this, CLICK));

		for (String s : typeLabel) {
			Container li = new EXContainer("li", "li");
			li.addChild(new EXContainer(s, "a").setAttribute("href", "#").setText(s).addEvent(this, CLICK));
			types.addChild(li);
		}

		for (String s : defaultDrives) {
			Container li = new EXContainer("li", "li");
			li.addChild(new EXContainer(s, "a").setAttribute("href", "#").setText(s).addEvent(this, CLICK));
			menuRight.addChild(li);
		}
		
		refreshList();
	}

	public void searchByText(String text) {

	}

	public void filterByType(String type) {

	}

	public void filterByFolder(String folder) {

	}

	public void filterByMyShare() {

	}

	public void share() {

	}

	public void addFolder(String name) {

	}

	public void addFile(File file) {
		respositoryService.save(file, Util.getRemoteUser());
	}

	public void duplicate(File file) {

	}

	public void refreshList() {
		listFile.getChildren().clear();
		listFile.setRendered(false);
		Page<File> files = respositoryService.getFiles(Util.getRemoteUser(), new PageRequest(this.currentPage, this.pageSize));
		for(File f : files.getContent()){
			listFile.addChild(new UIFile(f));
		}

	}

	public void addPage() {

	}

	public void saveOrUpdateMetadata(String key, String value) {

	}

	/**
	 * could be 1. delete 2. move 3. restore 4. edit 5. open with 6. share 7.
	 * 
	 * @param action
	 */
	public void execute(String action, Object... params) {

		if(selectedFile != null){ 
			if(action.equals("edit")){
				
				editor.openFile(selectedFile);
			}
		}

	}

	public class UIFile extends EXContainer implements Event {

		private File file;

		private Container link = new EXContainer("link", "a").setAttribute("href", "#");

		private Container img = new EXContainer("img", "img");

		private Container label = new EXContainer("name", "span").addClass("filename");

		public UIFile(File file) {
			super(file.getName(), "li");

			addChild(link);
			link.addChild(img);
			addChild(label);

			setFile(file);

			addEvent(this, CLICK);
		}

		public void setFile( File file) {

			this.file = file;
			// seting the label
			String truncatedLabel = file.getName();
			if (truncatedLabel.length() > 10) {
				truncatedLabel = truncatedLabel.substring(0, 10) + "...";
			}

			label.setText(truncatedLabel);
			setAttribute("title", file.getName());

			// setting the image
			String iconImage = getIconImage(file);
			img.setAttribute("src", "icons/" + iconImage);

		}

		public String getIconImage(File f) {
			return iconProvider.getIConImage(f);
		}

		public File getFile() {
			return file;
		}

		@Override
		public void ClientAction(JQuery container) {
			container.server(this);
		}

		@Override
		public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
			
			for(Container c  : listFile.getChildren()){
				c.removeClass("selected");
			}
			
			addClass("selected");
			selectedFile = this.file;
			return true;
		}

		@Override
		public void Success(JQuery container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			
		}

	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		//if(cont)
		
		execute(container.getName());
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {
		// TODO Auto-generated method stub

	}
}
