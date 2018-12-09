package com.laxtech.connectors.soapserviceconsumer.api;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

public class SoapClientConfig {

    @Parameter
    public String wsdlLocation;
    
    @Parameter
    public String service;

    @Parameter
    public String port;
    
    @Parameter
    @Optional(defaultValue="utf-8")
    public String encoding;

    
    @DisplayName("SOAP Version")
    @Parameter
    @Optional(defaultValue = "1.1")
    private String soapVersion;

    
	public String getWsdlLocation() {
		return wsdlLocation;
	}

	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getSoapVersion() {
		return soapVersion;
	}

	public void setSoapVersion(String soapVersion) {
		this.soapVersion = soapVersion;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "SoapClientConfig [wsdlLocation=" + wsdlLocation + ", service=" + service + ", port=" + port
				+ ", encoding=" + encoding + ", soapVersion=" + soapVersion + "]";
	}    
	
	
	
	
}
