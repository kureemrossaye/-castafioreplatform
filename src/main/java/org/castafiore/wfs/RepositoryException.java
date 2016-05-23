package org.castafiore.wfs;

public class RepositoryException extends WFSException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7375428406715650421L;

	public RepositoryException() {
		super();
		
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public RepositoryException(String message) {
		super(message);
		
	}

	public RepositoryException(Throwable cause) {
		super(cause);
		
	}

}
