package com.laxtech.connectors.calculator.api;

import static java.lang.System.lineSeparator;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * Representation of an HTTP response message attributes.
 *
 * @since 1.0
 */
public class HttpResponseAttributes extends HttpAttributes {

  private static final long serialVersionUID = -3131769059554988414L;

  /**
   * HTTP status code of the response. Former 'http.status'.
   */
  @Parameter
  private final int statusCode;

  /**
   * HTTP reason phrase of the response. Former 'http.reason'.
   */
  @Parameter
  private final String reasonPhrase;

  public HttpResponseAttributes(int statusCode, String reasonPhrase, MultiMap<String, String> headers) {
    super(headers);
    this.statusCode = statusCode;
    this.reasonPhrase = reasonPhrase;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(this.getClass().getName()).append(lineSeparator()).append("{").append(lineSeparator())
        .append(TAB).append("Status Code=").append(statusCode).append(lineSeparator())
        .append(TAB).append("Reason Phrase=").append(reasonPhrase).append(lineSeparator());

    buildMapToString(headers, "Headers", headers == null ? null : headers.entryList().stream(), builder);

    builder.append("}");

    return builder.toString();
  }
}