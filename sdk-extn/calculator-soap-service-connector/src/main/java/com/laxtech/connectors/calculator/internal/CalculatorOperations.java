package com.laxtech.connectors.calculator.internal;

import static java.lang.String.format;
import static org.mule.runtime.api.message.Message.of;
import static org.mule.runtime.api.metadata.DataType.BYTE_ARRAY;
import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import static org.mule.runtime.http.api.HttpConstants.Method.POST;
//import static org.mule.runtime.http.api.HttpHeaders.Names.CONTENT_LENGTH;
import static org.mule.runtime.http.api.HttpHeaders.Names.CONTENT_TYPE;
//import static org.mule.runtime.http.api.HttpHeaders.Names.TRANSFER_ENCODING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.error.MuleErrors;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.entity.InputStreamHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
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

import com.laxtech.connectors.calculator.api.HttpResponseAttributes;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class CalculatorOperations {

	private final Logger LOGGER = LoggerFactory.getLogger(CalculatorOperations.class);

	@Inject
	SoapService soapService;    

	@Inject
	private MuleContext muleContext;

	private static CalculatorHttpResponseToResult RESPONSE_TO_RESULT = new CalculatorHttpResponseToResult();

	private static final String API_URI = "http://dneonline.com/calculator.asmx";

	private static final String SOAP_ACTION_HEADER_VALUE_PREFIX = "http://tempuri.org/";
	private static final String SOAP_ACTION_HEADER_NAME = "SOAPAction";

	private static final String CONTENT_TYPE_HEADER = CONTENT_TYPE.toLowerCase();
//	private static final String TRANSFER_ENCODING_HEADER = TRANSFER_ENCODING.toLowerCase();
//	private static final String CONTENT_LENGTH_HEADER = CONTENT_LENGTH;

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config CalculatorConfiguration configuration,
			@Connection CalculatorConnection connection) {
		return "Using Configuration [" + configuration.getConfigId() + "] ";
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
	public InputStream add(int number1, int number2, String operation, @Content(primary = true) InputStream body1,
			@Config CalculatorConfiguration configuration,
			@Connection CalculatorConnection connection) {

		String bodyStr = null;
		try {
			bodyStr = convertStreamToString(body1, StandardCharsets.UTF_8);
		} catch (IOException exp) {
			LOGGER.error("Eror in deserializing object: " + exp);
		}

		LOGGER.debug("Add operation is:" + operation);
		LOGGER.debug("number1 is:" + number1);
		LOGGER.debug("number2 is:" + number2);
		LOGGER.debug("Add operation body is:" + bodyStr);
		LOGGER.debug("SoapClientConfig is:" + configuration.getSoapClientConfig().toString());
		bodyStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n"
				+ "  <soap12:Body>\r\n" + "    <Add xmlns=\"http://tempuri.org/\">\r\n" + "      <intA>" + number1
				+ "</intA>\r\n" + "      <intB>" + number2 + "</intB>\r\n" + "    </Add>\r\n" + "  </soap12:Body>\r\n"
				+ "</soap12:Envelope>";
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
			// SoapRequest request =
			// SoapRequest.builder().operation(operation).content(body).build();
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
	public int addition(int number1, int number2, String operation, @Content(primary = true) InputStream body1,
			@Config CalculatorConfiguration configuration,
			@Connection CalculatorConnection connection) {

		String bodyStr = null;
		try {
			bodyStr = convertStreamToString(body1, StandardCharsets.UTF_8);
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
		 * URL wsdlURL =
		 * CalculatorOperations.class.getResource("calculator.wsdl"); if
		 * (wsdlURL == null) { wsdlURL =
		 * CalculatorOperations.class.getClassLoader().getResource(
		 * "calculator.wsdl"); }
		 */
		URL wsdlURL = null;
		try {
			wsdlURL = new URL("http://dneonline.com/calculator.asmx?WSDL");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("URL format error", e);
		}
		Service service = Service.create(wsdlURL, SERVICE_NAME);

		// Calculator ss = new Calculator(wsdlURL, SERVICE_NAME);
		CalculatorSoap port = service.getPort(CalculatorSoap.class);

		LOGGER.debug("Invoking add.....");
		int _add__return = port.add(number1, number2);
		LOGGER.debug("add.result=" + _add__return);

		return _add__return;
	}

	@MediaType(value = ANY, strict = false)
	public void request(String action, @Content(primary = true) TypedValue<Object> body,
			@Config CalculatorConfiguration configuration,
			@Connection CalculatorConnection connection,
			CompletionCallback<InputStream, HttpResponseAttributes> callback) {

		LOGGER.debug("HttpClient is started.");
		try {

			HttpRequest httpRequest = createRequest(POST, API_URI, body, action);
			connection.sendRequest(httpRequest, callback).whenComplete((response, exception) -> {
				if (response != null) {
					LOGGER.debug("response is not null." + response);
					try {
						Result<InputStream, HttpResponseAttributes> result = RESPONSE_TO_RESULT.convert(muleContext,
								response, httpRequest.getUri());

						resendRequest(result, () -> {
							LOGGER.debug("result is:" + result);
							callback.success(result);
						});
					} catch (Exception e) {
						LOGGER.error("error is:" + e);
						callback.error(e);
					}
				} else {
					LOGGER.error("error occured:");
					LOGGER.error(getErrorMessage(httpRequest));
					String error = exception instanceof TimeoutException ? "TIMEOUT" : MuleErrors.CONNECTIVITY.name();
					callback.error(new Exception(httpRequest.getMethod() + " failed." + error, exception));
				}
			});

		} catch (IOException | TimeoutException e) {
			LOGGER.error("Error:", e);
		} catch (Exception exp) {
			LOGGER.error("Error:", exp);
		} finally {
			// disconnect();
		}
	}


	private HttpRequest createRequest(HttpConstants.Method method, String uri, TypedValue<Object> body, String action) {

		Object payload = body.getValue();
		Optional<Long> length = body.getLength();
		org.mule.runtime.api.metadata.MediaType mediaType = body.getDataType().getMediaType();

		HttpEntity httpEntity;
/*		if (!length.isPresent()) {
			httpEntity = new InputStreamHttpEntity((InputStream) payload);
			LOGGER.debug("length.isPresent().false." + length.isPresent());
		} else {
			// sanitizeForContentLength(requestBuilder, transferEncoding,
			// INVALID_TRANSFER_ENCODING_HEADER_MESSAGE);
			httpEntity = new InputStreamHttpEntity((InputStream) payload, length.get());
			LOGGER.debug("length.isPresent().true" + length.isPresent());
		}*/

		byte[] byteArrayContent = IOUtils.toByteArray((InputStream) payload);

		// content.getDataType().getMediaType().toString(), byteArrayContent.length
		httpEntity = new ByteArrayHttpEntity(byteArrayContent);
		// byte[] payloadBytes = getPayloadAsBytes(payload, transformationService);

		LOGGER.debug("Message body length is:" + byteArrayContent.length);
		LOGGER.debug("Message body is:" + new String(byteArrayContent));
		// httpEntity = new ByteArrayHttpEntity(byteArrayContent);

		// byte[] byteArrayContent = IOUtils.toByteArray(body);
		// LOGGER.debug("byteArrayContent:" + byteArrayContent);
		// ByteArrayHttpEntity httpEntity = new ByteArrayHttpEntity(byteArrayContent);
		// InputStreamHttpEntity httpEntity = new InputStreamHttpEntity(body);
		//MultiMap<String, String> parameterMap = new MultiMap<>();

		/*
		 * String payloadStr = null; try { payloadStr =
		 * convertStreamToString((InputStream) payload,
		 * mediaType.getCharset().orElse(StandardCharsets.UTF_8)); } catch (IOException
		 * exp) { LOGGER.error("Eror in deserializing object: " + exp); }
		 */

		LOGGER.debug("Action is:" + action);
		LOGGER.debug("Message body mediaType is:" + mediaType);
		// LOGGER.debug("SoapClientConfig is:" +
		// configuration.getSoapClientConfig().toString());

		HttpRequestBuilder builder = HttpRequest.builder();
		LOGGER.debug("Adding entity to builder.");
		if (httpEntity != null) {
			builder.entity(httpEntity);
			LOGGER.debug("Added entity to builder.");
		}
		/*
		 * try { LOGGER.debug("httpEntity:" +
		 * convertStreamToString(httpEntity.getContent(), StandardCharsets.UTF_8)); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		builder.addHeader(SOAP_ACTION_HEADER_NAME, SOAP_ACTION_HEADER_VALUE_PREFIX + action);
		if (body != null && !org.mule.runtime.api.metadata.MediaType.ANY.matches(mediaType)) {
			builder.addHeader(CONTENT_TYPE_HEADER, mediaType.toRfcString());
		}
		// builder.addHeader(CONTENT_TYPE_HEADER, "text/xml; charset=utf-8");
		// builder.addHeader(CONTENT_LENGTH_HEADER,
		// String.valueOf(byteArrayContent.length));
		// builder.addHeader("Host", "dneonline.com");
		// LOGGER.debug("Action header name:" + "SOAPAction");
		// LOGGER.debug("Action header value:" + SOAP_ACTION_HEADER_VALUE_PREFIX +
		// action);
		// LOGGER.debug("Message header name:" + CONTENT_TYPE_HEADER);
		// LOGGER.debug("Message header value:" +
		// builder.getHeaderValues(CONTENT_TYPE_HEADER));
		// LOGGER.debug("Action header name:" + CONTENT_LENGTH_HEADER);
		// LOGGER.debug("Action header value:" +
		// builder.getHeaderValue(CONTENT_LENGTH_HEADER));
		LOGGER.debug("Request Headers:" + builder.getHeaders());

		// builder.method(method).uri(uri).queryParams(parameterMap);
		// LOGGER.debug("method:" + method);
		// LOGGER.debug("uri:" + uri);
		builder.method(method).uri(uri);
		HttpRequest httpRequest = builder.build();
		return httpRequest;
	}

	

	private void resendRequest(Result result, Runnable notRetryCallback) {

		notRetryCallback.run();
	}

	private String getErrorMessage(HttpRequest httpRequest) {
		return format("Error sending HTTP request to %s", httpRequest.getUri());
	}
	
	private String convertStreamToString(InputStream inputStream, Charset charset) throws IOException {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	private byte[] getPayloadAsBytes(Object payload, TransformationService transformationService) {
		return (byte[]) transformationService.transform(of(payload), BYTE_ARRAY).getPayload().getValue();
	}
}
