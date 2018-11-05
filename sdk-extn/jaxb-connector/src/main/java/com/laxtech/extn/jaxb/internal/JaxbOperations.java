package com.laxtech.extn.jaxb.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laxtech.extn.jaxb.internal.pojo.Employee;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class JaxbOperations {

	private final Logger LOGGER = LoggerFactory.getLogger(JaxbConnectionProvider.class);

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config JaxbConfiguration configuration, @Connection JaxbConnection connection) {
		return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId()
				+ "]";
	}

	/**
	 * Publish message to Rabbitmq Queue
	 * 
	 * @param configuration
	 * @param connection
	 * @param message
	 */
	@MediaType(value = ANY, strict = false)
	public String publish(@Config JaxbConfiguration configuration, @Connection JaxbConnection connection,
			String message) {
		LOGGER.debug("Going to publish message:. " + message);
		//message = configuration.getConfigId() + ": " + message;
		Employee employee = buildObject(message);
		LOGGER.debug("String to object converted employee:" + employee);
		connection.publish(message.getBytes());
		String returnMessage = null;
		
		try {
			
			returnMessage = buildReturnMessage(employee);
			
		} catch (JAXBException e) {
			LOGGER.error("Error in building return message:" + e);
		}
		LOGGER.debug("Return message is:" + returnMessage);
		return returnMessage;
	}

	/**
	 * @param employee
	 * @return
	 * @throws JAXBException
	 */
	private String buildReturnMessage(Employee employee) throws JAXBException {

		employee.setSalary(new BigDecimal(employee.getSalary().doubleValue() * 1.05));
		JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(employee, sw);
        LOGGER.debug("buildReturnMessage().returnMessage(sw.toString()):" + sw.toString());
		return sw.toString();
		
	}

	/**
	 * @param xmlEmployee
	 * @return
	 */
	private Employee buildObject(String xmlEmployee) {

		JAXBContext jaxbContext;
		Employee employee = null;
		try {
			jaxbContext = JAXBContext.newInstance(Employee.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			employee = (Employee) jaxbUnmarshaller.unmarshal(new StringReader(xmlEmployee));
			System.out.println(employee);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
