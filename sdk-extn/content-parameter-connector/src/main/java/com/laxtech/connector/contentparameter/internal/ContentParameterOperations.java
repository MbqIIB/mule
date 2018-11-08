package com.laxtech.connector.contentparameter.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.List;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laxtech.connector.contentparameter.internal.pojo.Employee;
import com.laxtech.connector.contentparameter.internal.propgroup.HttpRequestBuilder;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class ContentParameterOperations {

	private final Logger LOGGER = LoggerFactory.getLogger(ContentParameterOperations.class);

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config ContentParameterConfiguration configuration,
			@Connection ContentParameterConnection connection) {
		return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId()
				+ "]";
	}

	/**
	 * Example of a simple operation that receives a string parameter and returns a
	 * new string message that will be set on the payload.
	 */
	@MediaType(value = ANY, strict = false)
	public String sayHi(String person) {
		return buildHelloMessage(person);
	}

	/**
	 * Private Methods are not exposed as operations
	 */
	private String buildHelloMessage(String person) {
		return "Hello " + person + "!!!";
	}

	public List<Employee> getEmployee(String path,
			@ParameterGroup(name = "requestBuilder", showInDsl = true) HttpRequestBuilder requestBuilder,
			@Config ContentParameterConfiguration configuration, @Connection ContentParameterConnection connection) {

		return connection.getEmployees(path, requestBuilder);
	}
}
