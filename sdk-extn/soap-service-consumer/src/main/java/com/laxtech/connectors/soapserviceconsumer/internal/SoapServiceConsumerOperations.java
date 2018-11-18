package com.laxtech.connectors.soapserviceconsumer.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;

import javax.inject.Inject;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.soap.api.SoapService;
import org.mule.runtime.soap.api.SoapVersion;
import org.mule.runtime.soap.api.client.SoapClient;
import org.mule.runtime.soap.api.client.SoapClientConfiguration;
import org.mule.runtime.soap.api.client.SoapClientConfigurationBuilder;
import org.mule.runtime.soap.api.message.SoapRequest;
import org.mule.runtime.soap.api.message.SoapResponse;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class SoapServiceConsumerOperations {

	@Inject
	SoapService soapService;

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config SoapServiceConsumerConfiguration configuration,
			@Connection SoapServiceConsumerConnection connection) {
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

	@MediaType(value = ANY, strict = false)
	public InputStream add(int number1, int number2, @Content(primary = true) InputStream body, 
			@Config SoapServiceConsumerConfiguration configuration,
			@Connection SoapServiceConsumerConnection connection) {

		SoapClientConfigurationBuilder configurationBuilder = SoapClientConfiguration.builder();

		SoapClientConfiguration soapClientConfiguration = configurationBuilder
				.withEncoding(configuration.getSoapClientConfig().getEncoding())
				.withWsdlLocation(configuration.getSoapClientConfig().getWsdlLocation())
				.withService(configuration.getSoapClientConfig().getService())
				.withVersion(SoapVersion.valueOf(configuration.getSoapClientConfig().getSoapVersion())).build();
		InputStream response = null;
		try {

			SoapClient soapClient = soapService.getClientFactory().create(soapClientConfiguration);
			SoapRequest request = SoapRequest.builder().content(body).build();
			SoapResponse soapResponse = soapClient.consume(request);
			response = soapResponse.getContent();

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

}
