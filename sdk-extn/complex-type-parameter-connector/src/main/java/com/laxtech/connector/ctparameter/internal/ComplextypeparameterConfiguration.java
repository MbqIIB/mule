package com.laxtech.connector.ctparameter.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across multiple operations since they represent something
 * core from the extension.
 */
@Operations(ComplextypeparameterOperations.class)
@ConnectionProviders(ComplextypeparameterDBConnectionProvider.class)
public class ComplextypeparameterConfiguration {

	@Parameter
	private String configId;

	public String getConfigId() {
		return configId;
	}

	@Parameter
	@Optional
	private Options options;

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

}
