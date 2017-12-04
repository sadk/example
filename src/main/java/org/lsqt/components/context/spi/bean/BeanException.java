package org.lsqt.components.context.spi.bean;

public class BeanException extends RuntimeException {
	private static final long serialVersionUID = -2089852170777652397L;

	public BeanException() {
	}

	public BeanException(String message) {
		super(message);
	}

	public BeanException(String message, Throwable paramThrowable) {
		super(message, paramThrowable);
	}

	public BeanException(Throwable paramThrowable) {
		super(paramThrowable);
	}

}