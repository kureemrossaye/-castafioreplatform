package org.castafiore.erp.ui.form.controls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

import com.Ostermiller.util.CSVPrinter;

public class EXPortButton extends EXButton implements CastafioreController, Event{

	private TableModel model_;
	
	public EXPortButton(TableModel model) {
		super("export", "Export");
		this.model_ = model;
		addEvent(this, CLICK);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/csv");
		response.setHeader( "Content-Disposition", "filename=" + "export.csv"+"\"" );
		int size = model_.getRowCount();
		int cols = model_.getColumnCount();
		java.io.OutputStream out = response.getOutputStream(); 
		CSVPrinter printer = new CSVPrinter(out);
		
		String[] headers = new String[cols];
		for(int j =0; j < cols;j++){
			headers[j] = model_.getColumnNameAt(j);
			
		}
		
		printer.writeln(headers);
		
		for(int i =0; i < size;i++){
			String[] line = new String[cols];
			for(int j =0; j < line.length; j++){
				Object val = model_.getValueAt(j, i, 0);
				if(val != null)
					line[j] = val.toString();
				else
					line[j] = null;
				
			}
			printer.writeln(line);
		}
		
		printer.flush();
		
		return null;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		String js = "window.open(\""+ResourceUtil.getMethodUrl(this)+"\", \"blank_\");";
		container.eval(js);
		
	}

}
