package com.paypal.bfs.test.employeeserv.api;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

	/**
	 * Retrieves the {@link Employee} resource by id.
	 *
	 * @param id
	 *            employee id.
	 * @return {@link Employee} resource.
	 */
	@RequestMapping("/v1/bfs/employees/{id}")
	ResponseEntity<Employee> employeeGetById(@PathVariable("id") String id);

	// ----------------------------------------------------------
	// TODO - add a new operation for creating employee resource.
	// ----------------------------------------------------------

	@RequestMapping(path = "/v1/bfs/add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.POST)
	ResponseEntity<Employee> addEmployee(@Valid @RequestBody final Employee e);
}
