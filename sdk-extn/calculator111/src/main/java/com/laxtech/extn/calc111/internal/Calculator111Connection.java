package com.laxtech.extn.calc111.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class Calculator111Connection {

  private final String id;

  public Calculator111Connection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
