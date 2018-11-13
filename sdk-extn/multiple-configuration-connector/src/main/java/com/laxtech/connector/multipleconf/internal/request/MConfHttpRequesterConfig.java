package com.laxtech.connector.multipleconf.internal.request;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an extension configuration, values set in this class
 * are commonly used across multiple operations since they represent something
 * core from the extension.
 */
@Configuration(name="requester")
@Operations(MConfHttpRequesterOperations.class)
@ConnectionProviders(MConfHttpRequesterConnectionProvider.class)
public class MConfHttpRequesterConfig implements Initialisable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MConfHttpRequesterConfig.class);

	@RefName
	private String configName;

	@Parameter
	private String configId;

	public String getConfigId() {
		return configId;
	}

	@Override
	public void initialise() throws InitialisationException {
		LOGGER.debug("Initializing config with name: " + configName);
	}
}
