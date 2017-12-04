package org.lsqt.components.mvc.spi.exception;

public class UrlMappingException extends RuntimeException{
	private static final long serialVersionUID = -6387666922296585064L;

	public UrlMappingException() {
	}

	public UrlMappingException(String paramString) {
		super(paramString);
	}

	public UrlMappingException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public UrlMappingException(Throwable paramThrowable) {
		super(paramThrowable);
	}
}
