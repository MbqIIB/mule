package com.laxtech.extn.rabbitmq.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class RabbitmqOperations {
	
	private final Logger LOGGER = LoggerFactory.getLogger(RabbitmqOperations.class);

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config RabbitmqConfiguration configuration, @Connection RabbitmqConnection connection) {
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
	public void publish(@Config RabbitmqConfiguration configuration, @Connection RabbitmqConnection connection,
			String message) {
		LOGGER.debug("Going to publish message:. " + message );
		message = configuration.getConfigId() + ": " + message;
		connection.publish(message.getBytes());
	}
}
