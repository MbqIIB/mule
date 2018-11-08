package com.laxtech.connector.contentparameter.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laxtech.connector.contentparameter.internal.pojo.Department;
import com.laxtech.connector.contentparameter.internal.pojo.Employee;
import com.laxtech.connector.contentparameter.internal.propgroup.ConnectionProperties;
import com.laxtech.connector.contentparameter.internal.propgroup.HttpRequestBuilder;


/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class ContentParameterConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(ContentParameterConnection.class);
	private ConnectionProperties properties;
	private String soapVersion;

	public ContentParameterConnection(ConnectionProperties properties, String soapVersion) {
		this.properties = properties;
		this.soapVersion = soapVersion;
	}

	public String getId() {
		return String.valueOf(properties.hashCode() + soapVersion.hashCode());
	}

	public String getEmployeeParameters() {

		return " Employee params [ Connection Properties " + properties.getHost() + ", " + properties.getPort()
				+ "], Soap Version " + soapVersion + "] ";
	}

	public void invalidate() {
		// do something to invalidate this connection!
	}

	public List<Employee> getEmployees(String path, HttpRequestBuilder requestBuilder) {

		LOGGER.debug("Request Path:" + path);
		LOGGER.debug(requestBuilder.toString());
		String body = null;
		try {
			body = convertStreamToString(requestBuilder.getBody(), StandardCharsets.UTF_8);
		} catch (IOException exp) {
			LOGGER.error("Eror in deserializing object: " + exp);
		}
		LOGGER.debug("Employee in request/body is:" + body);
		
		Employee body1 = null;
		try {
			body1 = convertStreamToObject(requestBuilder.getBody1());
		} catch (IOException | ClassNotFoundException exp) {
			LOGGER.error("Eror in deserializing object: " + exp);
		}
		LOGGER.debug("Employee in request/body1 is:" + body1);
		
		LOGGER.debug("Employee in request/employee is:" + requestBuilder.getEmployee());
		
		Map<String, String> empHeader = requestBuilder.getHeaders();
		LOGGER.debug("Employee in hearder is:" + empHeader);
		
		List<Employee> employees = new ArrayList<Employee>();
		Employee emp = new Employee();
		Department dept = new Department();
		dept.setId(10);
		dept.setName("IT");
		emp.setDepartment(dept);
		emp.setFirstName("David");
		emp.setLastName("Clutor");
		emp.setSalary(new BigDecimal(10000.0));
		emp.setId(100);
		employees.add(emp);

		emp = new Employee();
		dept = new Department();
		dept.setId(20);
		dept.setName("Finance");
		emp.setDepartment(dept);
		emp.setFirstName("John");
		emp.setLastName("Fisher");
		emp.setSalary(new BigDecimal(12000.0));
		emp.setId(200);
		employees.add(emp);
		LOGGER.debug("Returning employees:" + employees);
		return employees;

	}
		
	private Employee convertStreamToObject(InputStream input) throws IOException, ClassNotFoundException {
		Employee employee = null;
		try(ObjectInputStream objectInputStream =
			    new ObjectInputStream(input)){

				employee = (Employee) objectInputStream.readObject();
			}
		return employee;
	}

	private String convertStreamToString(InputStream inputStream, Charset charset) throws IOException {
	 
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
