package com.laxtech.extension.parametergroup.internal;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class ConnectionProperties {

    @Parameter
    @Optional(defaultValue="0.0.0.0")
    public String host;

    @Parameter
    @Optional(defaultValue="80")
    public Integer port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}	
}
