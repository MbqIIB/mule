package com.laxtech.connector.ctparameter.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class ComplextypeparameterDBConnection {

  private final String dbId;

  public ComplextypeparameterDBConnection(String dbId) {
    this.dbId = dbId;
  }

  public String getDbId() {
    return dbId;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
