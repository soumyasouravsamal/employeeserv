package com.paypal.bfs.test.employeeserv.repository;

import org.springframework.data.repository.CrudRepository;

import com.paypal.bfs.test.employeeserv.dto.EmployeeDto;

public interface EmployeeRepository extends CrudRepository<EmployeeDto, Integer>{

}
