package de.htw.sdf.photoplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.htw.sdf.photoplatform.exception.common.BaseException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested Ressource was not found!")
public class NotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5878839432690385704L;

	public NotFoundException(String msg) {
		super(msg);
		log.error(msg);
	}

}
