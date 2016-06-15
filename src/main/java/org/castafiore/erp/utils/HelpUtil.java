package org.castafiore.erp.utils;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import org.castafiore.erp.BaseErpModel;

public class HelpUtil {
	
	private final static Map<String, String> CACHE = new WeakHashMap<String, String>();
	
	public static String getHelp(Class<? extends BaseErpModel> clazz){
		if(true){
			return "";
		}
		
		try{
			
			if(CACHE.containsKey(clazz.getSimpleName())){
				return CACHE.get(clazz.getSimpleName());
			}else{
				String url = "http://localhost:8080/flexisoft/docs/" + clazz.getName().replace(".", "/") + ".html";
		
				Source source = new Source(new URL(url));
				//TextExtractor extractor = source.getAllElementsByClass("contentContainer").get(0).getAllElementsByClass("block").get(0).getTextExtractor();
				//extractor.setExcludeNonHTMLElements(false);
				String content =source.getAllElementsByClass("contentContainer").get(0).getAllElementsByClass("block").get(0).toString();
				CACHE.put(clazz.getSimpleName(), content);
				return content;
			}
		
		}catch(Exception e){
			e.printStackTrace();
			return "The help content is still being edited.";
		}
		
	}
	
	
	public static String getFieldHelp(Class<? extends BaseErpModel> clazz, String field){
		try{
			
			String cacheKey = clazz.getSimpleName() + "." + field;
 			if(CACHE.containsKey(cacheKey)){
				return CACHE.get(cacheKey);
			}else{
				String url = "http://localhost:8080/flexisoft/docs/" + clazz.getName().replace(".", "/") + ".html";
		
				Source source = new Source(new URL(url));
				
				List<Element> trs = source.getFirstElementByClass("overviewSummary").getAllElements("tr");
				for(Element element : trs){
					List<Element>  aas= element.getAllElements("a");
					if(aas.size() > 0 && aas.get(0).getTextExtractor().toString().equals(field)){
						String help = element.getFirstElementByClass("block").toString();
						CACHE.put(cacheKey, help);
						return help;
					}
				}
				
				return "The help content is still being edited.";
			}
		
		}catch(Exception e){
			//e.printStackTrace();
			return "The help content is still being edited.";
		}
	}

}
