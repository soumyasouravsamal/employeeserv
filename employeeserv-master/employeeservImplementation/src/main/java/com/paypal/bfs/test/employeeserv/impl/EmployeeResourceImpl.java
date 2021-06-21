package com.paypal.bfs.test.employeeserv.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

/**
 * Implementation class for employee resource.
 */
@RestController
@Validated
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeService service;

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {
		if (StringUtils.isBlank(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> addEmployee(Employee e) {
		if (e == null) {
			return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Employee>(service.save(e), HttpStatus.OK);
	}

}
