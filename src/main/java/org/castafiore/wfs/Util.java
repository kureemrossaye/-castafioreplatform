/*
 * Copyright (C) 2007-2010 Castafiore
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
 package org.castafiore.wfs;

public class Util {
	
	public static String getRemoteUser()
	{
		//return "arkureem";
//		Credential credential = SpringUtil.getBean("credential");
//		//SecurityService securityService = SpringUtil.getBean("securityService");
//		if(credential != null){
//		return credential.getRemoteUser();
//		}else{
//			return null;
//		}
		
		return "kureem";
		
	}
	
	
	public static String getLoggedOrganization(){
//		try{
//		Credential credential = SpringUtil.getBean("credential");
//		if(StringUtil.isNotEmpty(credential.getOrganization())){
//			return credential.getOrganization();
//		}else{
//			//throw new Exception("not logged yet");
//			return "flexisoft";
//		}
//		}catch(Exception e){
//			//e.printStackTrace();
//			return "flexisoft";
//		}
		
		return "archnetltd";
	}
	
	
//	public static boolean emailExists(String email){
//		try{
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//	       
//
//        HttpPost httpPost = new HttpPost("http://www.mailtester.com/testmail.php");
//        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//        nvps.add(new BasicNameValuePair("email", email));
//        nvps.add(new BasicNameValuePair("lang", "en"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        HttpResponse response2 = httpclient.execute(httpPost);
//
//        try {
//        	
//            HttpEntity e = response2.getEntity();
//           
//           String s = EntityUtils.toString(e);
//           if(s.contains("E-mail address is valid")){
//        	   return true;
//           }
//           
//        } finally {
//            httpPost.releaseConnection();
//        }
//        
//        return false;
//		}catch(Exception e){
//			throw new UIException(e);
//		}
//        
//	}

}
