package com.bitwormhole.starter4j.base;

public class StarterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StarterException() {
		super();
	}

	public StarterException(String msg) {
		super(msg);
	}

	public StarterException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public StarterException(Throwable cause) {
		super(cause);
	}

}
