package org.castafiore.portal.ui.editors;

import java.io.IOException;
import java.util.Properties;

import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.ex.form.AbstractStatefullComponent;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.File;

public class AceEditor extends AbstractStatefullComponent<String>{
	
	private final static Properties MODE_MAPPING = new Properties();
	
	static{
		try {
			MODE_MAPPING.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/properties/editor-mapping.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	private String theme_="monokai";
	
	private String mode_="text";
	
	public AceEditor(File file) {
		super(file.getName(), "div");
		String extension = ResourceUtil.getExtensionFromFileName(file.getName());
		String mode = MODE_MAPPING.getProperty(extension, "text");
		setMode(mode);
		setStyle("width", "800px").setStyle("height", "600px");
	}
	
	
	
	
	public void setMode(String mode){
		this.mode_ = mode;
		
		if(rendered()){
			JQuery j  = getJQuery();
			j.eval("if(editor_"+getId()+"){editor_"+getId()+".getSession().setMode(\"ace/mode/"+mode+"\");}");
			
			addCommand(j);
		}
	}
	
	public String getMode(){
		return mode_;
	}
	
	public void setTheme(String theme){
		this.theme_ = theme;
		if(rendered()){
			JQuery j  = getJQuery();
			j.eval("editor_"+getId()+".setTheme(\"ace/theme/"+theme_+"\");");
		
			addCommand(j);
		}
	}
	
	
	public void onReady(JQuery jquery){
		
	//	String js = "editor_"+getId()+" = CodeMirror(document.getElementById(\""+getId()+"\"), { mode: \"text/html\",extraKeys: {\"Ctrl-Space\": \"autocomplete\"},value: document.getElementById(\""+getId()+"\").innerHTML});";
		// String js = "editor_"+getId()+" = CodeMirror(document.getElementById(\""+getId()+"\"), { mode: \"text/html\",extraKeys: {\"Ctrl-Space\": \"autocomplete\"}});";
		 
		//jquery.eval(js);
		String js = " var editor_"+getId()+" = ace.edit(\""+getId()+"\");editor_"+getId()+".setTheme(\"ace/theme/"+theme_+"\");editor_"+getId()+".getSession().setMode(\"ace/mode/"+mode_+"\");";
		
		jquery.eval(js);
		//jquery.getScript("ace/src-min-noconflict/ace.js", jquery.clone().eval(js));
	}




	@Override
	public void setValue(String value) {
		setText(value);
	}


	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getValue() {
		return getText();
	}
}
