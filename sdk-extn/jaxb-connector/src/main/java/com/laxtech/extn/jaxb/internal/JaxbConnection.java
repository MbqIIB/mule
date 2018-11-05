package com.laxtech.extn.jaxb.internal;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class JaxbConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(JaxbConnection.class);

	private ConnectionFactory factory = new ConnectionFactory();
	private Connection connection;
	private Channel channel;
	private String queueName;

	public void publish(byte[] payload) {
		try {
			LOGGER.debug("Going to publish message:. " + new String(payload) + " into queue:" + queueName);
			channel.basicPublish("", queueName, null, payload);
		} catch (IOException e) {
			LOGGER.error("Coop Governance logging failed. " + e.getMessage());
		}
	}

	public JaxbConnection(String hostname, String queueName) {
		factory.setHost(hostname);
		this.queueName = queueName;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
		} catch (Exception e) {
			channel = null;
			connection = null;
			LOGGER.error("Coop Governance logging connection failed. " + e.getMessage());
		}
	}

	public String getId() {
		if (connection != null) {
			return String.valueOf(connection.hashCode());
		} else {
			return "No connection";
		}
	}

	public void disconnect() {
		try {
			channel.close();
			connection.close();
		} catch (Exception e) {
			LOGGER.warn("Queue disconnect failed. " + e.getMessage());
		} finally {
			channel = null;
			connection = null;
		}
	}

	public boolean isConnected() {
		return channel != null;
	}

}
