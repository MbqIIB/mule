package com.laxtech.extn.calc.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(CalculatorOperations.class)
@ConnectionProviders(CalculatorConnectionProvider.class)
public class CalculatorConfiguration {

  @Parameter
  private String configId;

  public String getConfigId(){
    return configId;
  }
  
  public String getV1() {
	return v1;
}

public void setV1(String v1) {
	this.v1 = v1;
}

public String getV2() {
	return v2;
}

public void setV2(String v2) {
	this.v2 = v2;
}

public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

@Parameter
  @Optional(defaultValue="15") 
  public String v1;
  
  @Parameter
  @Optional(defaultValue="5") 
  public String v2;
  
  @Parameter
  @Optional(defaultValue="SUBSTRACT") 
  public String action;
}
