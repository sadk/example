package org.lsqt.components.mvc.impl;

public class ApplicationException extends RuntimeException{
	private static final long serialVersionUID = -6387666922296585064L;

	public ApplicationException() {
	}

	public ApplicationException(String paramString) {
		super(paramString);
	}

	public ApplicationException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public ApplicationException(Throwable paramThrowable) {
		super(paramThrowable);
	}
}
