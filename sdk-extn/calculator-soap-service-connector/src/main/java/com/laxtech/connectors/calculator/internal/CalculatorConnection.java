package com.laxtech.connectors.calculator.internal;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.mule.runtime.core.api.MuleContext;


import org.mule.runtime.extension.api.error.MuleErrors;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laxtech.connectors.calculator.api.HttpResponseAttributes;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class CalculatorConnection {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CalculatorConnection.class);


    private HttpClient httpClient;
    private String token;
	private int connectionTimeout;


	public CalculatorConnection(String token, int connectionTimeout, HttpService httpService, ProxyConfig proxyConfig) {
		initHttpClient(httpService, proxyConfig);
		this.token = token;
		this.connectionTimeout = connectionTimeout;
	}

	private void initHttpClient(HttpService httpService, ProxyConfig proxyConfig) {
		HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
		if (proxyConfig != null) {
			builder.setProxyConfig(proxyConfig);
		}
		builder.setName("calculator");
		httpClient = httpService.getClientFactory().create(builder.build());
		httpClient.start();
	}

	public void disconnect() {
		httpClient.stop();
	}
	
	public CompletableFuture<HttpResponse>  sendRequest(HttpRequest httpRequest, CompletionCallback<InputStream, HttpResponseAttributes> callback)
			throws IOException, TimeoutException {
		// parameterMap.put("token", "token");

		LOGGER.debug("HttpRequest" + httpRequest);
		/*
		 * LOGGER.debug("HttpRequest.entity" +
		 * convertStreamToString(httpRequest.getEntity().getContent(),
		 * StandardCharsets.UTF_8));
		 */
		LOGGER.debug("Sending request.");
		return httpClient.sendAsync(httpRequest, connectionTimeout, true, null);
	}

}
