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

package org.castafiore.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.castafiore.ComponentNotFoundException;
import org.castafiore.Constant;
import org.castafiore.resource.BinaryFileData;
import org.castafiore.resource.FileData;
import org.castafiore.ui.Application;
import org.castafiore.ui.ApplicationRegistry;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.CastafioreEngine;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.interceptors.InterceptorRegistry;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 */

@Controller
@RequestMapping(value="/castafiore")
public class CastafioreController implements ResourceLoaderAware {
	
	@Autowired
	private ApplicationRegistry applicationRegistry;

	
	@Autowired
	private InterceptorRegistry interceptorRegistry;
	
	private ResourceLoader resourceLoader;


	public ApplicationRegistry getApplicationRegistry() {
		return applicationRegistry;
	}


	public void setApplicationRegistry(ApplicationRegistry applicationRegistry) {
		this.applicationRegistry = applicationRegistry;
	}


	public InterceptorRegistry getInterceptorRegistry() {
		return interceptorRegistry;
	}


	public void setInterceptorRegistry(InterceptorRegistry interceptorRegistry) {
		this.interceptorRegistry = interceptorRegistry;
	}


	@SuppressWarnings("unchecked")
	private Map<String, String> getParameterMap(Map parameters)
	{
		
		Map<String, String> result = new HashMap<String, String>(parameters.size());
		Iterator iter = parameters.keySet().iterator();
		
		while(iter.hasNext())
		{
			String key = iter.next().toString();
			
			String value = ((String[])parameters.get(key))[0].toString();
			
			result.put(key, value);
		}
		
		return result;
	}
	
	
	/**
	 * Instantiate a new instance of FileData to hold uploaded data
	 * @return
	 */
	private  FileData getNewInstance(){
		try
		{
			Class cls = Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.wfs.types.BinaryFile");
			return (FileData)cls.newInstance();
		}
		catch(ClassNotFoundException e){
			return new BinaryFileData();
		}
		catch(Exception e){
			return new BinaryFileData();
		}
	}
	
	
	

	protected void doGeter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession();
    	FileUploadListener listener = null; 
	    StringBuffer buffy = new StringBuffer();
    	long bytesRead = 0,	contentLength = 0; 
    	
    	
    	if (session == null)
    	{
    		return;
    	}
    	else if (session != null)
    	{
    		// Check to see if we've created the listener object yet
    		listener = (FileUploadListener)session.getAttribute("LISTENER");
    		
    		if (listener == null)
    		{
    			return;
    		}
    		else
    		{
    			// Get the meta information
    	    	bytesRead = listener.getBytesRead();
    			contentLength = listener.getContentLength();
    		}
    	}
    	
    	response.setContentType("text/xml");
	    
	    buffy.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
	    buffy.append("<response>\n");
	    buffy.append("\t<bytes_read>" + bytesRead + "</bytes_read>\n");
	    buffy.append("\t<content_length>" + contentLength + "</content_length>\n");

	    // Check to see if we're done
	    if (bytesRead == contentLength) 
	    {
		    buffy.append("\t<finished />\n");
		    
		    // No reason to keep listener in session since we're done
			session.setAttribute("LISTENER", null);
	    }
	    else
	    {
	    	// Calculate the percent complete
		    long percentComplete = ((100 * bytesRead) / contentLength);  
	
		    buffy.append("\t<percent_complete>" + percentComplete + "</percent_complete>\n");
	    }
		
	    buffy.append("</response>\n");
	    
	    out.println(buffy.toString());
	    out.flush();
	    out.close();
	}





	/**
	 * handles upload made by EXUpload component
	 * @param request
	 * @param response
	 * @throws ServletException
	 */
	private void handleMultipartRequest(HttpServletRequest request, ServletResponse response)throws ServletException
	{
		//logger.debug("handling multipart request. A file is being uploaded....");
		try
		{
			//Map pro = BaseSpringUtil.getBean("uploadprops");
	    	
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory(1024, new File("")));
			
			// set file upload progress listener
			FileUploadListener 
				listener = new FileUploadListener();
			
			HttpSession 
				session = request.getSession();
			
			
			session.setAttribute("LISTENER", listener);
			
			// upload servlet allows to set upload listener
			upload.setProgressListener(listener);
			
			
			Iterator iter =upload.parseRequest(request).iterator();
			//FileItemIterator iter = upload.getItemIterator(request);
			String applicationId = null;
	    	String componentId = null;
	    	EXUpload exUpload = null;
	    	Application applicationInstance = null;
	    	List<FileData> ds = new ArrayList<FileData>();
	    	//String name = null;
			while (iter.hasNext()) 
			{
			    FileItem item = (FileItem)iter.next();
			    
			    
			    if (item.isFormField()) {
			        if(item.getFieldName().equals("casta_applicationid")){
			        	applicationId = IOUtil.getStreamContentAsString(item.getInputStream());
			        	//logger.debug("applicationid for upload:" + applicationId);
			        	applicationInstance = (Application)request.getSession().getAttribute(applicationId);
			        }
			        else if(item.getFieldName().equalsIgnoreCase("casta_componentid")){
			        	componentId = IOUtil.getStreamContentAsString(item.getInputStream());
			        	//logger.debug("componentid for upload:" + componentId);
			        }
			    } 
			    else 
			    {
			    	FileData bFile = null;
			    	System.out.println(item.getName());
			    	String name = item.getName();
			    	//logger.debug("opening client stream...");
			    	File savedFile = new File("/" +new Date().getTime() + "_" + item.getName()); //new File(request.getRealPath("/")+"uploadedFiles/"+name);
			    	
			    	item.write(savedFile);
			    	String contentType = item.getContentType();
			    				    	
			    	bFile = getNewInstance();
			    	bFile.setUrl(savedFile.getAbsolutePath());
			    	bFile.setName(item.getName());
			    	bFile.setName(name);
			    	ds.add(bFile);
			    }
			}
			System.out.println(componentId);
			exUpload = (EXUpload)applicationInstance.getDescendentById(componentId);
			System.out.println(exUpload);

			
			System.out.println(ds);
			exUpload.setValue(ds);
			exUpload.fireCompleted();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	private CastafioreEngine getEngine(HttpServletRequest req){
		CastafioreEngine engine = (CastafioreEngine)req.getSession().getAttribute("CastafioreEngine");
		if(engine == null){
			engine = new CastafioreEngine(interceptorRegistry);
			req.getSession().setAttribute("CastafioreEngine", engine);
			return (CastafioreEngine)req.getSession().getAttribute("CastafioreEngine");
		}else{
			return engine;
		}
	}
	

	
	@RequestMapping("/ui")
	public void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 
		boolean isMultipart = ServletFileUpload.isMultipartContent((HttpServletRequest)request);
		ComponentUtil.loadApplication((HttpServletRequest)request, (HttpServletResponse)response, applicationRegistry);
		
		if(isMultipart)
		{
			handleMultipartRequest((HttpServletRequest)request, response);
		}else if("true".equals(request.getParameter("upload"))){
			doGeter(request, response);
			
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			
			Map<String, String> params = this.getParameterMap(request.getParameterMap());
			
			String componentId = request.getParameter("casta_componentid");
			String eventId = request.getParameter("casta_eventid");
			String applicationId = request.getParameter("casta_applicationid");
			
			Assert.notNull(applicationId, "cannot execute a castafiore request when the applicationid is null. Please verify that the parameter casta_applicationid has been set correctly in tag, jsp or whatever");

			Application applicationInstance = CastafioreApplicationContextHolder.getCurrentApplication();
			String script = "";

			if((componentId == null && eventId == null && applicationInstance != null))
			{ 
				applicationInstance.setRendered(false);
				script = getEngine(request).getJQuery(applicationInstance, "root_" + applicationId , applicationInstance, new ListOrderedMap());
				script = script + "hideloading();";
				
			}
			else if((componentId != null && eventId != null)&& applicationInstance != null){	
				try{
					script = getEngine(request).executeServerAction(componentId,  applicationInstance, "root_" + applicationId, params);
					script = script + "hideloading();";
				}catch(ComponentNotFoundException cnfe){
					script = "window.location.reload( false );";
				}
			}else if((componentId != null && eventId != null)&& applicationInstance == null){
				script = "alert('Your session has expired. Browser will refresh');window.location.reload( false );";
			}
			
			Set<String> requiredScript = applicationInstance.getBufferedResources();
			if(requiredScript != null && requiredScript.size() > 0)
			{
				StringBuilder reqScript = new StringBuilder();
				reqScript.append(Constant.NO_CONFLICT+ ".plugin('"+applicationInstance.getId()+"',{").append("files:[");
				int scount = 0;
				for(String s : requiredScript)
				{
					reqScript.append("'").append(s).append("'");
					scount++;
					if(scount < requiredScript.size())
					{
						reqScript.append(",");
					}
				}
				reqScript.append("],").append("selectors:['"+Constant.ID_PREF+"root_"+applicationId+"'],");
				reqScript.append("callback:function(){").append(script).append("}").append("});");
				reqScript.append(Constant.NO_CONFLICT + ".plugin('"+applicationInstance.getId()+"').get();");
				script = reqScript.toString();
			}
			applicationInstance.flush(12031980);
			script ="<script>" + Constant.NO_CONFLICT +"(document).ready(function(){" + script + "});</script>";
			
			response.getOutputStream().write(script.getBytes());
		}
	}
	
	
	@RequestMapping(value="/resource/{type}/{path}")
	public void doResource(@PathVariable(value="type")String type,@PathVariable(value="path") String path, HttpServletRequest request, HttpServletResponse response){
		String spec = null;
		try
		{
			//spec = request.getParameter("spec");
			spec = type + ":" + path;
			//logger.debug(spec);
			//ResourceLocator locator = ResourceLocatorFactory.getResourceLocator(spec);
			//String width = request.getParameter("width");
			Resource f = resourceLoader.getResource(type + ":" + path);
			if(f != null)
			{
				try
				{
					OutputStream os = response.getOutputStream();
					//response.setContentType(f.getMimeType());
					((HttpServletResponse)response).setHeader("Content-Disposition", "filename=" + path); 
					ChannelUtil.TransferData(f.getInputStream(), os);
					os.flush();	 
				}
				catch(ClassCastException cce)
				{
					throw new Exception("the file specified by the path " + spec + " is not a binary file");
				}
			}
			else
			{
				 throw new Exception("the file specified by the path " + spec + " cannot be found. Possibly deleted");
			}
		}
		catch(Exception e)
		{
			if(spec == null)
				throw new UIException("unable to load resource since the specification passed is null" ,e);
			else
				throw new UIException("unable to load resource with the specification " + spec ,e);
		}
	}


	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		
	}
}
