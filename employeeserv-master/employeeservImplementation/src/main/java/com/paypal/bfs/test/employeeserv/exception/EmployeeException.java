package com.paypal.bfs.test.employeeserv.exception;

public class EmployeeException extends RuntimeException {

	private static final long serialVersionUID = 7082008662159047140L;

	public EmployeeException() {
		super();
	}

	public EmployeeException(final String msg) {
		super(msg);
	}

	public EmployeeException(final Exception e) {
		super(e);
	}

	public EmployeeException(final String msg, final Exception e) {
		super(msg, e);
	}

}
