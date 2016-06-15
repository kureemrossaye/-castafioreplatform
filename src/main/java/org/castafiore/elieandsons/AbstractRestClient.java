package org.castafiore.elieandsons;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.castafiore.KeyValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractRestClient {
	
	DefaultHttpClient httpclient= new DefaultHttpClient();
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public String send(String uri) throws IOException{
		HttpPost httpost = new HttpPost(uri);
		HttpResponse response = httpclient.execute(httpost);
		return EntityUtils.toString(response.getEntity());
	}
	
	public String send(String uri, Object body)throws IOException{
		String json = mapper.writeValueAsString(body);
		
		HttpPost httpost = new HttpPost(uri);
		
		httpost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		
		
		HttpResponse response = httpclient.execute(httpost);
		return EntityUtils.toString(response.getEntity());
		
	}
	
	public String toJson(Object o)throws IOException{
		return mapper.writeValueAsString(o);
	}
	
	public List createList(String json, Class type)throws Exception{
		
		Object[] da = (Object[])mapper.readValue(json,type);
		List result = new ArrayList();
		for(Object o : da){
			result.add(o);
		}
		
		return result;
		
	}
	
	public List createList(InputStream json, Class type)throws Exception{
		
		Object[] da = (Object[])mapper.readValue(json,type);
		List result = new ArrayList();
		for(Object o : da){
			result.add(o);
		}
		
		return result;
		
	}
	
	public <T extends Object> T create(String json, Class<? extends T> type) throws IOException{
		return mapper.readValue(json.getBytes(), type);
	}
	
	public String createUri(String point, String method, KeyValuePair...params){
		String endpoint = point +  "/castafiore/methods?controller=orderscontroller&";
		 endpoint = endpoint + "action=" + method.toLowerCase();
		if(params != null && params.length >0){
			for(KeyValuePair kv : params){
				endpoint = endpoint + "&" + kv.getKey() + "=" +URLEncoder.encode(kv.getValue());
			}
		}
		return endpoint;
	}

}
