package pt.ulisboa.tecnico.rnl.dei.deiint.exceptions;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(DeiintException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public DeiintExceptionDto deiintException(DeiintException e) {
		return new DeiintExceptionDto(e);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public DeiintExceptionDto accessDenied(AccessDeniedException e) {
		return new DeiintExceptionDto(e);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public DeiintExceptionDto unexpectedException(Exception e) {
		logger.error(e.getMessage(), e);
		return new DeiintExceptionDto(e);
	}
}
