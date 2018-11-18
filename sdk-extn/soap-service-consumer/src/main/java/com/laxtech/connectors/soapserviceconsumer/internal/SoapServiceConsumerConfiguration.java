package com.laxtech.connectors.soapserviceconsumer.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import com.laxtech.connectors.soapserviceconsumer.api.SoapClientConfig;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(SoapServiceConsumerOperations.class)
@ConnectionProviders(SoapServiceConsumerConnectionProvider.class)
public class SoapServiceConsumerConfiguration {

  @Parameter
  private String configId;
  
  @Parameter
  private SoapClientConfig soapClientConfig;
  
  public String getConfigId(){
    return configId;
  }

public SoapClientConfig getSoapClientConfig() {
	return soapClientConfig;
}

public void setSoapClientConfig(SoapClientConfig soapClientConfig) {
	this.soapClientConfig = soapClientConfig;
}

  
}
