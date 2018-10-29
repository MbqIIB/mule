package com.laxtech.extn.calc110.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class Calculator110Connection {

  private final String id;

  public Calculator110Connection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
