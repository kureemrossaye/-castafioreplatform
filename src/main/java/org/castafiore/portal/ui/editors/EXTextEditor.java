package org.castafiore.portal.ui.editors;

import org.castafiore.ui.ex.panel.EXModal;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.types.File;

public class EXTextEditor extends EXModal {

	private AceEditor editor = null;

	public EXTextEditor(String name) {
		super(name, "New File");
		pack();
		setStyle("width", "800px").setStyle("height", "475px");
	}

	public void openFile(File file) {
		setTitle(file.getName());
		if (editor == null) {
			editor = new AceEditor(file);
			setBody(editor);
		}

		try {
			editor.setValue(IOUtil.getStreamContentAsString(file.getInputStream()));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		
		open();
	}
	


}
