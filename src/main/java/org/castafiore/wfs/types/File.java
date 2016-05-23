package org.castafiore.wfs.types;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.util.Assert;

@NodeEntity
public class File {

	@GraphId
	private Long id;

	private String absolutePath;

	private File parent;

	private String name;

	private String title;

	private String url;

	private Date dateModified = new Date();

	private Date dateCreated = new Date();

	private long size;

	private String clazz = getClass().getName();

	private String editPermissions = null;

	private String readPermissions = null;

	private String owner = null;

	private Boolean locked = false;

	protected List<File> children = null;

	// La phrase produit ou slogan
	private String summary;

	// Autres info
	private String detail;

	// utilite
	private String tags;

	private Double likeCount = 1d;

	private Boolean commentable = true;

	private Boolean ratable = true;

	private String destination;

	private String mimeType;
	
	private Boolean deleted = false;
	
	private String type;

	public File() {

	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Double getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Double likeCount) {
		this.likeCount = likeCount;
	}

	public Boolean getCommentable() {
		return commentable;
	}

	public void setCommentable(Boolean commentable) {
		this.commentable = commentable;
	}

	public Boolean getRatable() {
		return ratable;
	}

	public void setRatable(Boolean ratable) {
		this.ratable = ratable;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getLastModified() {
		return dateModified;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public File getParent() {
		return parent;
	}

	public void setParent(File parent) {
		this.parent = parent;
	}

	public String getClazz() {
		return clazz;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	protected void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	@Override
	public String toString() {
		return getAbsolutePath();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof File)) {
			return false;
		}
		try {
			File f = (File) o;
			if (f.getAbsolutePath() == null && getAbsolutePath() == null) {
				return f.getName().equals(getName());
			} else if (f.getAbsolutePath() == null && getAbsolutePath() != null) {
				return f.getName().equals(getName());
			} else if (f.getAbsolutePath() != null && getAbsolutePath() != null) {
				return f.getAbsolutePath().equals(getAbsolutePath());
			} else if (f.getAbsolutePath() != null && getAbsolutePath() == null) {
				return false;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	protected void addChild(File file) {

		Assert.notNull(file, "Cannot add a null file");
		addChild(file.getName(), file);
	}

	private void addChild(String name, File file) {
		Assert.notNull(name, "name cannot be null");
		Assert.notNull(file, "Cannot add a null file");
		// set permissions
		if (file.getEditPermissions() == null) {
			file.setEditPermissions(getEditPermissions());
		}

		if (file.getReadPermissions() == null) {
			file.setReadPermissions(getReadPermissions());
		}

		// set same owner as sessions owner
		if (file.getOwner() == null)
			file.setOwner(Util.getRemoteUser());

		if (file.getOwner() == null) {
			try {
				file.setOwner(Util.getRemoteUser());
			} catch (Exception e) {

			}
		}

		file.absolutePath = getAbsolutePath() + "/" + name;
		children.add(file);
		file.setParent(this);

	}

	public String getEditPermissions() {
		return editPermissions;
	}

	public void setEditPermissions(String editPermissions) {
		this.editPermissions = editPermissions;
	}

	public String getReadPermissions() {
		return readPermissions;
	}

	public void setReadPermissions(String readPermissions) {
		this.readPermissions = readPermissions;
	}

	public boolean isLocked() {
		if (locked == null) {
			locked = false;
		}
		return locked;
	}

	public List<File> getChildren() {
		return children;
	}

	public void setChildren(List<File> children) {
		this.children = children;
	}

	public Date getDateModified() {
		return dateModified;
	}
	
	
	
	
	
	public OutputStream getOutputStream()throws Exception{
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new java.io.File(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_')), false),1024);
		setUrl(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_'));
		return out;
		
	}

	
	public void append(byte[] bytes)throws Exception{
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new java.io.File(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_')), true),1024);
		out.write(bytes);
		out.flush();
		out.close();
		setUrl(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_'));
		
	}
	public InputStream getInputStream() throws Exception{
		System.out.println("loading data from :" + url);
		if(url != null){
			return new BufferedInputStream(new FileInputStream(new java.io.File(url)));
		}
		
		return null;
		
	}

	
	public void write(byte[] binaryData)throws Exception{
		write(new ByteArrayInputStream(binaryData));
	}
	
	public void write(InputStream in)throws Exception {
		OutputStream baop = getOutputStream();
		
		ChannelUtil.TransferData(in, baop);
		baop.flush();
		setUrl(ResourceUtil.getDirToWrite() + "/" + getAbsolutePath().replace('/', '_'));
		//write( baop.toByteArray());
		baop.close();
		setSize(1024);
		
	}
	


	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setName(String name) {
		
		this.name = name;
		String extension = ResourceUtil.getExtensionFromFileName(name);
		if(extension.length() > 0)
			this.mimeType = ResourceUtil.getMimeFromExtension(extension);
	}
	
	
	public File getChild(String name){
		for(File f : getChildren()){
			if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
