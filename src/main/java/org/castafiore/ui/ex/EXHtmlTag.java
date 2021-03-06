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

package org.castafiore.ui.ex;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.html.HTMLTag;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.util.Assert;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public abstract class EXHtmlTag extends EXComponent implements HTMLTag, Container {

	protected Map<String, String> attributes_ = new LinkedHashMap<String, String>(0);

	private Map<String, String> styles_ = new LinkedHashMap<String, String>(0);

	protected Set<String> changedStyles_ = new LinkedHashSet<String>(0);

	protected Set<String> changedAttributes_ = new LinkedHashSet<String>(0);
	
	protected JQuery command = null;

	private String tag_;

	private String text_ = "";

	public EXHtmlTag(String name, String tagName) {
		super(name);
		this.setAttribute("name", name);
		this.tag_ = tagName;
	}

	public void flush(int secretKey){
		command = null;
	}

	public String getAttribute(String name) {
		return StringUtil.getValue(name, this.attributes_);
	}
	
	public Container addCommand(JQuery jquery){
		if(command == null){
			command = jquery;
		}else{
			command.append(jquery);
		}
		return this;
	}
	
	public JQuery getJQuery(){
		return new JQuery("#" + getId());
	}
	
	public JQuery getCommand(){
		return command;
	}

	public abstract String getHTML();

	public String getStyle(String name) {
		return StringUtil.getValue(name, styles_);
	}

	public String getTag() {
		return tag_;
	}

	public Container setAttribute(String name, Var value) {
		Assert.notNull(name, "you cannot pass a name as null to set an attribute");

		if (value == null) {

			attributes_.remove(name);
		} else {
			String txt = value.getJavascript();
			attributes_.put(name, "js::-" + txt);
			changedAttributes_.add(name);
		}
		return this;
	}

	public Container setAttribute(String name, String value) {

		Assert.notNull(name, "you cannot pass a name as null to set an attribute");
		if (value == null) {
			if (attributes_.containsKey(name)) {
				attributes_.remove(name);
				setRendered(false);
			}
		} else {
			String cur = attributes_.get(name);
			if (value.equals(cur)) {

			} else {
				attributes_.put(name, value);
				changedAttributes_.add(name);
			}
		}
		return this;
	}

	public Container setStyle(String name, String value) {
		Assert.notNull(name, "you cannot pass a name as null to set a style");
		if (value == null) {

			styles_.remove(name);
			setRendered(false);
		} else {
			styles_.put(name, value);
			changedStyles_.add(name);
		}
		return this;
	}

	public Container setStyle(String name, Var var) {
		Assert.notNull(name, "you cannot pass a name as null to set a style");
		if (var == null) {
			styles_.remove(name);
			setRendered(false);
		} else {
			String txt = var.getJavascript();
			styles_.put(name, txt);
			changedStyles_.add(name);
		}
		return this;
	}

	public String[] getAttributeNames() {
		return this.attributes_.keySet().toArray(new String[attributes_.keySet().size()]);
	}

	public String[] getStyleNames() {
		return this.styles_.keySet().toArray(new String[styles_.keySet().size()]);
	}

	public String[] getChangedAttributeNames() {
		return changedAttributes_.toArray(new String[changedAttributes_.size()]);

	}

	public String[] getChangedStyleNames() {
		return changedStyles_.toArray(new String[changedStyles_.size()]);
	}

	public String getText(boolean escape) {
		if (text_ == null) {
			return "";
		}
		if (escape)
			return JavascriptUtil.javaScriptEscape(text_.trim());
		else
			return text_;

	}

	public String getText() {
		return getText(true);
	}

	public Container setText(String text_) {
		this.text_ = text_;
		setRendered(false);
		return this;
	}

}
