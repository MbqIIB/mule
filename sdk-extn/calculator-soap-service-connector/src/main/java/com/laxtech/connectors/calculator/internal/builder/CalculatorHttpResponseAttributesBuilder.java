package com.laxtech.connectors.calculator.internal.builder;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.laxtech.connectors.calculator.api.HttpResponseAttributes;

/**
 * Creates {@link HttpResponseAttributes} based on an {@HttpResponse} and it's
 * parts.
 */
public class CalculatorHttpResponseAttributesBuilder {

	HttpResponse response;

	public CalculatorHttpResponseAttributesBuilder setResponse(HttpResponse response) {
		this.response = response;
		return this;
	}

	public HttpResponseAttributes build() {
		int statusCode = response.getStatusCode();
		String reasonPhrase = response.getReasonPhrase();

		return new HttpResponseAttributes(statusCode, reasonPhrase, response.getHeaders());
	}
}
