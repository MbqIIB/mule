package com.laxtech.connectors.soapserviceconsumer.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.soap.api.SoapService;
import org.mule.runtime.soap.api.SoapVersion;
import org.mule.runtime.soap.api.client.SoapClient;
import org.mule.runtime.soap.api.client.SoapClientConfiguration;
import org.mule.runtime.soap.api.client.SoapClientConfigurationBuilder;
import org.mule.runtime.soap.api.message.SoapRequest;
import org.mule.runtime.soap.api.message.SoapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.CalculatorSoap;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class SoapServiceConsumerOperations {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SoapServiceConsumerOperations.class);

	@Inject
	SoapService soapService;
	
	@Inject
	HttpService httpService;

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
	public InputStream add(int number1, int number2, String operation, @Content(primary = true) InputStream body, 
			@Config SoapServiceConsumerConfiguration configuration,
			@Connection SoapServiceConsumerConnection connection) {

		String bodyStr = null;
		try {
			bodyStr = convertStreamToString(body, StandardCharsets.UTF_8);
		} catch (IOException exp) {
			LOGGER.error("Eror in deserializing object: " + exp);
		}
		
		LOGGER.debug("Add operation is:" + operation);
		LOGGER.debug("number1 is:" + number1);
		LOGGER.debug("number2 is:" + number2);
		LOGGER.debug("Add operation body is:" + bodyStr);		
		LOGGER.debug("SoapClientConfig is:" + configuration.getSoapClientConfig().toString());
		bodyStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" + 
				"  <soap12:Body>\r\n" + 
				"    <Add xmlns=\"http://tempuri.org/\">\r\n" + 
				"      <intA>" + number1 + "</intA>\r\n" + 
				"      <intB>" + number2 + "</intB>\r\n" + 
				"    </Add>\r\n" + 
				"  </soap12:Body>\r\n" + 
				"</soap12:Envelope>";
		LOGGER.debug("Add operation body is:" + bodyStr);		
		SoapClientConfigurationBuilder configurationBuilder = SoapClientConfiguration.builder();

		SoapClientConfiguration soapClientConfiguration = configurationBuilder
				.withEncoding(configuration.getSoapClientConfig().getEncoding())
				.withWsdlLocation(configuration.getSoapClientConfig().getWsdlLocation())
				.withService(configuration.getSoapClientConfig().getService())
				.withPort(configuration.getSoapClientConfig().getPort())
				.withVersion(SoapVersion.valueOf(configuration.getSoapClientConfig().getSoapVersion())).build();
		InputStream response = null;
		try {

			SoapClient soapClient = soapService.getClientFactory().create(soapClientConfiguration);
			//SoapRequest request = SoapRequest.builder().operation(operation).content(body).build();
			SoapRequest request = SoapRequest.builder().operation(operation).content(bodyStr).build();
			SoapResponse soapResponse = soapClient.consume(request);
			response = soapResponse.getContent();

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	@MediaType(value = ANY, strict = false)
	public int addition(int number1, int number2, String operation, @Content(primary = true) InputStream body, 
			@Config SoapServiceConsumerConfiguration configuration,
			@Connection SoapServiceConsumerConnection connection) {

		String bodyStr = null;
		try {
			bodyStr = convertStreamToString(body, StandardCharsets.UTF_8);
		} catch (IOException exp) {
			LOGGER.error("Eror in deserializing object: " + exp);
		}
		
		LOGGER.debug("Add operation is:" + operation);
		LOGGER.debug("number1 is:" + number1);
		LOGGER.debug("number2 is:" + number2);
		LOGGER.debug("Add operation body is:" + bodyStr);		
		LOGGER.debug("SoapClientConfig is:" + configuration.getSoapClientConfig().toString());
		
	    QName SERVICE_NAME = new QName("http://tempuri.org/", "Calculator");
/*
	    URL wsdlURL = SoapServiceConsumerOperations.class.getResource("calculator.wsdl");
        if (wsdlURL == null) {
        	wsdlURL = SoapServiceConsumerOperations.class.getClassLoader().getResource("calculator.wsdl");
        }
        */
        URL wsdlURL = null;
		try {
			wsdlURL = new URL("http://dneonline.com/calculator.asmx?WSDL");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("URL format error", e);
		}
        Service service = Service.create(wsdlURL, SERVICE_NAME);

        //Calculator ss = new Calculator(wsdlURL, SERVICE_NAME);
        CalculatorSoap port = service.getPort(CalculatorSoap.class);

        LOGGER.debug("Invoking add.....");
        int _add__return = port.add(number1, number2);
        LOGGER.debug("add.result=" + _add__return);

		return _add__return;
	}
	
	private String convertStreamToString(InputStream inputStream, Charset charset) throws IOException {
		 
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

}
