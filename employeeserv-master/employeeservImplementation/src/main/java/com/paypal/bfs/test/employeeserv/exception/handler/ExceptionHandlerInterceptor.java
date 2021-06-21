package com.paypal.bfs.test.employeeserv.exception.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.RollbackException;
import javax.validation.ConstraintViolation;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paypal.bfs.test.employeeserv.exception.EmployeeException;

@RestControllerAdvice
public class ExceptionHandlerInterceptor extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("message", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<Object> handleEmployeeException(EmployeeException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("error", "Bad request Error.");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", "Mandatory field missing Exception");
		body.put("message", ex.getConstraintName());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(javax.validation.ConstraintViolationException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", "Mandatory field missing Exception");
		body.put("message", ex.getConstraintViolations());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler({ TransactionSystemException.class })
	public ResponseEntity<Object> handleTransactionSystemException(Exception ex, WebRequest request) {
		Throwable t = ((TransactionSystemException) ex).getRootCause();
		Map<String, Object> body = new LinkedHashMap<>();
		if (t instanceof ConstraintViolationException) {
			String constraintViolations = ((ConstraintViolationException) t).getConstraintName();
			body.put("timestamp", LocalDateTime.now());
			body.put("error", "Mandatory field missing Exception");
			body.put("message", constraintViolations);
		}
		if (t instanceof javax.validation.ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((javax.validation.ConstraintViolationException) t)
					.getConstraintViolations();
			body.put("timestamp", LocalDateTime.now());
			body.put("error", "Mandatory field missing Exception");
			body.put("message", constraintViolations.toString());
		}
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler({ RollbackException.class })
	public ResponseEntity<Object> handleRollbackException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		Throwable t = ((RollbackException) ex).getCause();
		Map<String, Object> body = new LinkedHashMap<>();
		if (t instanceof TransactionSystemException) {
			return handleTransactionSystemException(ex, request);
		}
		if (t instanceof ConstraintViolationException) {
			String constraintViolations = ((ConstraintViolationException) t).getConstraintName();
			body.put("timestamp", LocalDateTime.now());
			body.put("error", "Mandatory field missing Exception");
			body.put("message", constraintViolations);
		}
		if (t instanceof javax.validation.ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((javax.validation.ConstraintViolationException) t)
					.getConstraintViolations();
			body.put("timestamp", LocalDateTime.now());
			body.put("error", "Mandatory field missing Exception");
			body.put("message", constraintViolations);
		}
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
