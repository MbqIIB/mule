package com.laxtech.connectors.soapserviceconsumer.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class SoapServiceConsumerConnection {

  private final String id;

  public SoapServiceConsumerConnection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
