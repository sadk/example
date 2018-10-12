
package org.lsqt.components.mvc;


public class InitializationException extends RuntimeException{

	private static final long serialVersionUID = 462987941278840492L;

	public InitializationException(String msg) {
		super(msg);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

	public InitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

