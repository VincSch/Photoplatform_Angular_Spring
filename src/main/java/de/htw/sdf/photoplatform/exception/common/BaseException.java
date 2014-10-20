package de.htw.sdf.photoplatform.exception.common;

import org.apache.log4j.Logger;

public class BaseException extends Exception {

	protected Logger log = Logger.getLogger(this.getClass().getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(String msg) {
		super(msg);
	}
}
