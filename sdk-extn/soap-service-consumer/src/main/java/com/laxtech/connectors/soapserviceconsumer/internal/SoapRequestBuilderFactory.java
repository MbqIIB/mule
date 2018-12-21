package com.laxtech.connectors.soapserviceconsumer.internal;

import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import static org.mule.runtime.http.api.HttpConstants.Method.GET;
import static java.lang.String.valueOf;

import java.util.concurrent.CompletableFuture;

public class SoapRequestBuilderFactory {
	private static final String API_URI = "https://slack.com/api/";

    private HttpClient httpClient;
    private String token;
    private int responseTimeout;

    public SoapRequestBuilderFactory(HttpClient httpClient, String token) {
        this(httpClient, token, 5000);
    }

    public SoapRequestBuilderFactory(HttpClient httpClient, String token, int responseTimeout) {
        this.httpClient = httpClient;
        this.token = token;
        this.responseTimeout = responseTimeout;
    }

    public SlackRequestBuilder newRequest(String slackMethod) {
        return new SlackRequestBuilder(slackMethod);
    }

    public class SlackRequestBuilder {
        private String uri;
        private MultiMap<String, String> params;

        SlackRequestBuilder(String slackMethod) {
            this.params = new MultiMap<>();
            this.params.put("token", token);
            this.uri = API_URI + slackMethod;
        }

        public SlackRequestBuilder withOptionalParam(String name, Object value) {
            if (value != null) {
                this.params.put(name, valueOf(value));
            }
            return this;
        }

        public CompletableFuture<HttpResponse> sendAsyncRequest() {
            return httpClient.sendAsync(HttpRequest.builder()
                    .method(GET)
                    .uri(uri)
                    .queryParams(params)
                    .build(), responseTimeout, true, null);
        }

}
}
