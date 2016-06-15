package org.castafiore.erp.ui.form.controls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.js.JArray;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ContractSearchControl extends EXAutoComplete implements AutoCompleteSource, InputControl<String>{
	
	private static ObjectMapper mapper = new ObjectMapper();

	public ContractSearchControl(String name, String value) {
		super(name, value);
		
		setSource(this);
		
	}

	@Override
	public JArray getSource(String param) {
		
		try{
		String url ="http://elie-svr.dyndns.org:8080/elie/castafiore/methods?controller=orderscontroller&action=suggestions&term="+param;
		String response = readUrl(url);
		String[] as = mapper.readValue(response, String[].class);
		JArray result = new JArray();
		for(String s : as){
			result.add(s);
		}
		return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JArray();
	}
	
	
	public static String readUrl(String url)throws Exception{
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));

		String inputLine;
		StringBuilder b = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			b.append(inputLine).append("\n");
		in.close();
		return b.toString();

	}

	@Override
	public void fillVo(BaseErpModel model) {
		BeanUtil.setProperty(model, getProperty(), getValue());
		
	}

	@Override
	public void fillControl(BaseErpModel model) {
		Object  o = BeanUtil.getProperty(model, getProperty());
		if(o == null){
			setValue("");
		}else{
		setValue(o.toString());
		}
		
	}

	@Override
	public String getProperty() {
		return getName();
	}

	

	
}
