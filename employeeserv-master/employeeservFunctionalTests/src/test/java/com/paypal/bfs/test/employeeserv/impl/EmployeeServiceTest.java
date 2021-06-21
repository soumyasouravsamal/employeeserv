package com.paypal.bfs.test.employeeserv.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class)
@AutoConfigureMockMvc
public class EmployeeServiceTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetEmployee() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		final Employee e = getEmpFromJson(mvcResult.getResponse().getContentAsString());
		Assert.assertNotNull(e);
		Assert.assertEquals(new Integer(1), e.getId());
		Assert.assertEquals("FIRST NAME", e.getFirstName());
		Assert.assertEquals("LAST NAME", e.getLastName());
		Assert.assertNotNull(e.getAddress());
		Assert.assertEquals(new Integer(1), e.getAddress().getAdrId());
		Assert.assertEquals("CITY", e.getAddress().getCity());
		Assert.assertEquals("COUNTRY", e.getAddress().getCountry());
		Assert.assertEquals("LINE1", e.getAddress().getLine1());
		Assert.assertEquals("LINE2", e.getAddress().getLine2());
		Assert.assertEquals("STATE", e.getAddress().getState());
		Assert.assertEquals(new Integer(100), e.getAddress().getZipcode());

	}

	@Test
	public void testGetEmployeeFail() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/2").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assert.assertEquals(404, mvcResult.getResponse().getStatus());
		Assert.assertTrue(mvcResult.getResponse().getContentAsString()
				.contains("\"message\":\"No employee found with id : 2\",\"error\":\"Bad request Error.\""));

	}

	@Test
	public void testGetEmployeeNull() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get("/v1/bfs/employees").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assert.assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testAddFailure() throws Exception {
		Employee emp = getCreatedEmployee();
		emp.setFirstName(null);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/v1/bfs/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getJson(emp))).andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
		Assert.assertTrue(mvcResult.getResponse().getContentAsString()
				.contains("\"error\":\"Mandatory field missing Exception\",\"message\":\""
						+ "[ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=firstName, "
						+ "rootBeanClass=class com.paypal.bfs.test.employeeserv.dto.EmployeeDto, "
						+ "messageTemplate='{javax.validation.constraints.NotNull.message}'}]\""));
	}

	@Test
	public void testAddFailureAddr() throws Exception {
		Employee emp = getCreatedEmployee();
		Address a = emp.getAddress();
		a.setLine1(null);
		emp.setAddress(a);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/v1/bfs/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getJson(emp))).andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
		Assert.assertTrue(mvcResult.getResponse().getContentAsString()
				.contains("\"error\":\"Mandatory field missing Exception\",\"message\":\""
						+ "[ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=line1, "
						+ "rootBeanClass=class com.paypal.bfs.test.employeeserv.dto.AddressDto, "
						+ "messageTemplate='{javax.validation.constraints.NotNull.message}'}]\""));
	}

	@Test
	public void testAddAndGetEmployee() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/v1/bfs/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getJson(getCreatedEmployee()))).andReturn();

		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		final Employee e = getEmpFromJson(mvcResult.getResponse().getContentAsString());
		Assert.assertNotNull(e);
		Assert.assertEquals(new Integer(3), e.getId());
		Assert.assertEquals("Soumya", e.getFirstName());
		Assert.assertEquals("Samal", e.getLastName());
		Assert.assertNotNull(e.getAddress());
		Assert.assertEquals(new Integer(4), e.getAddress().getAdrId());
		Assert.assertEquals("Bangalore", e.getAddress().getCity());
		Assert.assertEquals("India", e.getAddress().getCountry());
		Assert.assertEquals("Whitefield", e.getAddress().getLine1());
		Assert.assertEquals("Borewell Road", e.getAddress().getLine2());
		Assert.assertEquals("KA", e.getAddress().getState());
		Assert.assertEquals(new Integer(560066), e.getAddress().getZipcode());

	}

	@Test
	public void testAddEmployeeNull() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/v1/bfs/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(getJson(null))).andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
	}

	private String getJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private Employee getEmpFromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, Employee.class);
	}

	private Employee getCreatedEmployee() {
		Employee empStub = new Employee();
		empStub.setFirstName("Soumya");
		empStub.setLastName("Samal");
		Address addr = new Address();
		addr.setCity("Bangalore");
		addr.setCountry("India");
		addr.setLine1("Whitefield");
		addr.setLine2("Borewell Road");
		addr.setState("KA");
		addr.setZipcode(560066);
		empStub.setAddress(addr);
		return empStub;
	}

}
