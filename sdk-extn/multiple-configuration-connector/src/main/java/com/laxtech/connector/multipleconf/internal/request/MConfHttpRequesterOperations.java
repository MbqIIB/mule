package com.laxtech.connector.multipleconf.internal.request;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;

import com.laxtech.connector.multipleconf.internal.listener.MConfHttpListenerConfig;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MConfHttpRequesterOperations {

  private static final String REQUEST = "Request";


  /**
   * Example of a simple operation that receives a string parameter and returns a new string message that will be set on the payload.
   */
  @MediaType(value = ANY, strict = false)
  public String request(String person, @Config MConfHttpRequesterConfig configuration, 
		  @Connection MConfHttpRequesterConnection connection,  
		  @ParameterGroup(name = REQUEST) HttpRequesterRequestBuilder requestBuilder) {
    return buildHelloMessage(person);
  }

  /**
   * Private Methods are not exposed as operations
   */
  private String buildHelloMessage(String person) {
    return "Hello " + person + "!!!";
  }
}
