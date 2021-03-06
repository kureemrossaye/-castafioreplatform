/*
 * Copyright (C) 2007-2008 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.castafiore.ui.js;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.KeyValuePair;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.utils.JavascriptUtil;

/**
 * Represents a javascript map. This class should be used in conjunction with {@link JQuery} to transfer javascript variable
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public  class JMap implements JSObject, Serializable {
	
	
	@SuppressWarnings("unchecked")
	private Map<Object, String> internal_ = new ListOrderedMap();
	
	public void putAll(JMap map)
	{
		internal_.putAll(map.internal_);
	}
	
	
	public JMap putAll(String key,List<KeyValuePair> pairs){
		JMap map  = new JMap();
		for(KeyValuePair kv : pairs){
			
			map.put(kv);
		}
		
		putAll(map);
		return this;
	}
	
	public JMap put(KeyValuePair kv){
		put(kv.getKey(), kv.getValue());
		return this;
	}
	/**
	 * puts a simple string
	 * @param key
	 * @param s
	 * @return
	 */
	public JMap put(Var key ,String s)
	{
		internal_.put(key, "\"" + JavascriptUtil.javaScriptEscape( s) + "\"");
		return this;  
	}
	
	
	
	public JMap put(Var key, boolean bool){
		internal_.put(key, bool + "");
		return this;
	}
	
	public JMap put(Var key, Number numeric)
	{
		internal_.put(key, numeric + "");
		return this;
	}
	
	/**
	 * merges the specified {@link JMap} with this one
	 * @param map
	 */

	
	
	/**
	 * puts a {@link JArray} in the map
	 * @param key
	 * @param array
	 * @return
	 */
	public JMap put(Var key,JArray array)
	{
		internal_.put(key, array.getJavascript());
		return this;
	}
	
	/**
	 * puts a jmap in the map
	 * @param key
	 * @param jmap
	 * @return
	 */
	public JMap put(Var key, JMap jmap)
	{
		internal_.put(key, jmap.getJavascript());
		return this;
	}
	
	
	/**
	 * puts a function
	 * @param key
	 * @param functionjs
	 * @return
	 */
	public JMap put( Var key, JQuery functionjs, String... params)
	{
		
		StringBuilder function = new StringBuilder();
		
		StringBuilder sParams = new StringBuilder();
		if(params != null)
		{
			int i = 0;
			for(String s : params)
			{
				sParams.append(s);
				
				if(i < params.length -1)
				{
					sParams.append(",");
				}
				i++;
			}
		}
		
		
		function.append("function("+sParams.toString()+"){").append("\n").append(functionjs.getCompleteJQuery()).append("\n").append("}");
		
		internal_.put(key, function.toString());
		
		return this;
	}
	
	/**
	 * puts a variable
	 * @param key
	 * @param var
	 * @return
	 */
	public JMap put(Var key,Var var)
	{
		internal_.put(key, var.getJavascript());
		
		return this;
	}
	

	/**
	 * puts a simple string
	 * @param key
	 * @param s
	 * @return
	 */
	public JMap put(String key ,String s)
	{
		internal_.put(key, "\"" + JavascriptUtil.javaScriptEscape( s) + "\"");
		return this;  
	}
	
	
	
	public JMap put(String key, boolean bool){
		internal_.put(key, bool + "");
		return this;
	}
	
	public JMap put(String key, Number numeric)
	{
		internal_.put(key, numeric + "");
		return this;
	}
	
	/**
	 * merges the specified {@link JMap} with this one
	 * @param map
	 */

	
	
	/**
	 * puts a {@link JArray} in the map
	 * @param key
	 * @param array
	 * @return
	 */
	public JMap put(String key,JArray array)
	{
		internal_.put(key, array.getJavascript());
		return this;
	}
	
	/**
	 * puts a jmap in the map
	 * @param key
	 * @param jmap
	 * @return
	 */
	public JMap put(String key, JMap jmap)
	{
		internal_.put(key, jmap.getJavascript());
		return this;
	}
	
	
	public JMap put(String key, List<? extends JMap> list){
		JArray array = new JArray();
		for(JMap m : list){
			array.add(m);
		}
		
		put(key, array);
		return this;
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JMap putAll(Map<String,?> map){
		for(String key : map.keySet()){
			Object val = map.get(key);
			if(val instanceof Number){
				put(key, (Number)val);
			}else if(val instanceof String){
				put(key, (String)val);
			}else if(val instanceof Boolean){
				put(key, (Boolean)val);
			}else if(val instanceof JSObject){
				put(key, (JSObject)val);
			}else if(val instanceof List){
				put(key,(List)val);
			}
		}
		
		return this;
	}
	
	public JMap put(String key, JSObject val){
		internal_.put(key, val.getJavascript());
		return this;
	}
	
	
	/**
	 * puts a function
	 * @param key
	 * @param functionjs
	 * @return
	 */
	public JMap put( String key, JQuery functionjs, String... params)
	{
		
		StringBuilder function = new StringBuilder();
		
		StringBuilder sParams = new StringBuilder();
		if(params != null)
		{
			int i = 0;
			for(String s : params)
			{
				sParams.append(s);
				
				if(i < params.length -1)
				{
					sParams.append(",");
				}
				i++;
			}
		}
		
		
		function.append("function("+sParams.toString()+"){").append("\n").append(functionjs.getCompleteJQuery()).append("\n").append("}");
		
		internal_.put(key, function.toString());
		
		return this;
	}
	
	/**
	 * puts a variable
	 * @param key
	 * @param var
	 * @return
	 */
	public JMap put(String key,Var var)
	{
		
		internal_.put(key, var.getJavascript());
		
		return this;
	}
	
	/**
	 * returns the compiled hashmap
	 * @return
	 */
	public Map<Object, String> getCompiled()
	{
		return this.internal_;
	}
	
	
	/**
	 * generates the necessary JSON string to be executed on the browser
	 * @return
	 */
	public String getJavascript()
	{
		Iterator<Object> iterKey = this.internal_.keySet().iterator();
		StringBuilder result = new StringBuilder();
		result.append("{");
		while(iterKey.hasNext())
		{
			Object key = iterKey.next();
			
			
			
			String value = this.internal_.get(key);
			
			if(key instanceof String)
				result.append("\"").append(key).append("\"").append(":").append(value);
			else if(key instanceof Var)
				result.append("").append(((Var)key).getJavascript()).append("").append(":").append(value);
		
			
			if(iterKey.hasNext())
			{
				result.append(",");
			}
			
		}
		result.append("}");
		return result.toString();
	}
	
	/**
	 * checks if this {@link JMap} is empty
	 * @return
	 */
	public boolean isEmpty()
	{
		return this.internal_.isEmpty();
	}


}
