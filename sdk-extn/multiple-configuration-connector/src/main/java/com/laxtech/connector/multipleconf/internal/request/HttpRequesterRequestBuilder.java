package com.laxtech.connector.multipleconf.internal.request;


	
	import java.io.InputStream;
	import java.io.Serializable;
	import java.util.Map;

	import org.mule.runtime.extension.api.annotation.param.Content;
	import org.mule.runtime.extension.api.annotation.param.NullSafe;
	import org.mule.runtime.extension.api.annotation.param.Optional;
	import org.mule.runtime.extension.api.annotation.param.Parameter;


	public class HttpRequesterRequestBuilder {

	    @Parameter
	    @Content(primary = true)
	    private InputStream body;
	    
   
	    @Parameter
	    @Content
	    @Optional 
	    @NullSafe
	    private Map<String, String> uriParams;
	    
	    @Parameter
	    @Content
	    @Optional 
	    @NullSafe
	    private Map<String, String> headers;

	    @Parameter
	    @Content
	    @Optional 
	    @NullSafe
	    private Map<String, String> queryParams;

	    @Parameter
	    @Content
	    @Optional 
	    @NullSafe
	    private Map<String, String> searchParams;

		public InputStream getBody() {
			return body;
		}

		public void setBody(InputStream body) {
			this.body = body;
		}

		public Map<String, String> getUriParams() {
			return uriParams;
		}

		public void setUriParams(Map<String, String> uriParams) {
			this.uriParams = uriParams;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Map<String, String> headers) {
			this.headers = headers;
		}

		public Map<String, String> getQueryParams() {
			return queryParams;
		}

		public void setQueryParams(Map<String, String> queryParams) {
			this.queryParams = queryParams;
		}

		public Map<String, String> getSearchParams() {
			return searchParams;
		}

		public void setSearchParams(Map<String, String> searchParams) {
			this.searchParams = searchParams;
		}


		@Override
		public String toString() {
			return "HttpRequestBuilder [body=" + body + ", uriParams="
					+ uriParams + ", headers=" + headers + ", queryParams=" + queryParams + ", searchParams=" + searchParams
					+ "]";
		}

}
