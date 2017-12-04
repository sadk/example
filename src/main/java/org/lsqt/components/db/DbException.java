package org.lsqt.components.db;

public class DbException extends RuntimeException{
		
	private static final long serialVersionUID = -5794794397370431167L;

	public DbException(String msg) {
		super(msg);
	}

	public DbException(Throwable cause) {
		super(cause);
	}

	public DbException(String msg, Throwable cause) {
		super(msg, cause);
	}
		
}
