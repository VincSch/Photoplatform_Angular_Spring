package de.htw.sdf.photoplatform.exception.common;

public class ServiceException extends AbstractBaseException {

	public ServiceException(int code) {
		super(code, "Internal Service Exception");
	}

}
