package org.lsqt.components.codegen;

public class ReverseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6538066817954654326L;

	public ReverseException(){
		super();
	}
	
	public ReverseException(String msg) {
		super(msg);
	}
	

    public ReverseException(Throwable cause) {
        super(cause);
    }

    public ReverseException(String message, Throwable cause) {
        super(message, cause);
    }
}
