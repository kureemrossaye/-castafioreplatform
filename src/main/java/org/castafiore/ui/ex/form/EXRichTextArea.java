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
 package org.castafiore.ui.ex.form;

import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.js.JMap;

public class EXRichTextArea extends EXTextArea {
	
	

	public EXRichTextArea(String name) {
		super(name);
	}
	
	public void onReady(JQuery proxy){
		
		
		//proxy.appendJSFragment("CKEDITOR.replace( '"+getId()+"' );");
		proxy.invoke("cleditor", new JMap().put("width", "auto").put("height", "auto"));
		

		
	}
	
	
	
	
}
