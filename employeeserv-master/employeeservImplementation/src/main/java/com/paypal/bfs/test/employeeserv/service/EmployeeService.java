package com.paypal.bfs.test.employeeserv.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dto.AddressDto;
import com.paypal.bfs.test.employeeserv.dto.EmployeeDto;
import com.paypal.bfs.test.employeeserv.exception.EmployeeException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Transactional
	public Employee save(final Employee e) {
		return convertDtoToBean(repository.save(convertBeanToDto(e)));
	}

	public EmployeeDto getDtoById(String id) throws EmployeeException {
		return repository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new EmployeeException("No employee found with id : " + id));
	}

	public Employee getById(String id) throws EmployeeException {
		return convertDtoToBean(getDtoById(id));
	}

	private EmployeeDto convertBeanToDto(final Employee e) {
		final EmployeeDto d = new EmployeeDto();
		d.setFirstName(e.getFirstName());
		d.setLastName(e.getLastName());
		d.setAddress(convertBeanToDto(e.getAddress()));
		return d;
	}

	private AddressDto convertBeanToDto(final Address e) {
		final AddressDto d = new AddressDto();
		d.setCity(e.getCity());
		d.setCountry(e.getCountry());
		d.setLine1(e.getLine1());
		d.setLine2(e.getLine2());
		d.setState(e.getState());
		d.setZipCode(e.getZipcode());
		return d;
	}

	private Employee convertDtoToBean(final EmployeeDto d) {
		final Employee e = new Employee();
		e.setLastName(d.getLastName());
		e.setFirstName(d.getFirstName());
		e.setId(d.getId());
		e.setAddress(convertDtoToBean(d.getAddress()));
		return e;
	}

	private Address convertDtoToBean(final AddressDto d) {
		final Address a = new Address();
		a.setCity(d.getCity());
		a.setCountry(d.getCountry());
		a.setAdrId(d.getId());
		a.setLine1(d.getLine1());
		a.setLine2(d.getLine2());
		a.setState(d.getState());
		a.setZipcode(d.getZipCode());
		return a;
	}

}
