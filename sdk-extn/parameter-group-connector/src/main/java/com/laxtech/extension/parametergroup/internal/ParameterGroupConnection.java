package com.laxtech.extension.parametergroup.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class ParameterGroupConnection {

	private ConnectionProperties properties;
	private String soapVersion;
	
  public ParameterGroupConnection(ConnectionProperties properties, String soapVersion) {
	this.properties = properties;
	this.soapVersion = soapVersion;
}

  public String getId() {
	  return String.valueOf(properties.hashCode() + soapVersion.hashCode());
  }
  
  public String getEmployeeParameters() {
	  
	 return " Employee params [ Connection Properties " + properties.getHost() + ", " + 
			 properties.getPort() + "], Soap Version " + soapVersion + "] ";	  
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
