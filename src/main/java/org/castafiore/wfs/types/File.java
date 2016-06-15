package org.castafiore.wfs.types;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.ResourceUtil;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class File {

	@GraphId
	private Long id;

	private String drive;

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

	private List<Metadata> metadata = new LinkedList<Metadata>();

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

	public String getClazz() {
		return clazz;
	}

	public Date getDateCreated() {
		return dateCreated;
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

	public Date getDateModified() {
		return dateModified;
	}

	public OutputStream getOutputStream() throws Exception {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				new java.io.File(ResourceUtil.getDirToWrite() + "/" + getId()), false),
				1024);
		setUrl(ResourceUtil.getDirToWrite() + "/" + getId());
		return out;

	}

	public void append(byte[] bytes) throws Exception {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				new java.io.File(ResourceUtil.getDirToWrite() + "/" + getId()), true),
				1024);
		out.write(bytes);
		out.flush();
		out.close();
		setUrl(ResourceUtil.getDirToWrite() + "/" + getId());

	}

	public InputStream getInputStream() throws Exception {
		System.out.println("loading data from :" + url);
		if (url != null) {
			return new BufferedInputStream(new FileInputStream(new java.io.File(url)));
		}

		return null;

	}

	public void write(byte[] binaryData) throws Exception {
		write(new ByteArrayInputStream(binaryData));
	}

	public void write(InputStream in) throws Exception {
		OutputStream baop = getOutputStream();

		ChannelUtil.TransferData(in, baop);
		baop.flush();
		setUrl(ResourceUtil.getDirToWrite() + "/" + getId());
		// write( baop.toByteArray());
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
		if (extension.length() > 0)
			this.mimeType = ResourceUtil.getMimeFromExtension(extension);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
	}

	public String getDrive() {
		return drive;
	}

	public void setDrive(String drive) {
		this.drive = drive;
	}
	
	

}
