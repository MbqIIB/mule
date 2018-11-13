package com.laxtech.connector.multipleconf.internal.listener;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Expression;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Configuration(name="listener")
@Sources(MConfHttpListenerSource.class)
@ConnectionProviders(MConfHttpListenerConnectionProvider.class)
public class MConfHttpListenerConfig implements Initialisable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MConfHttpListenerConfig.class);


	@RefName
	private String configName;
	
  /**
   * Base path to use for all requests that reference this config.
   */
  @Parameter
  @Optional
  @Expression(NOT_SUPPORTED)
  private String basePath;

  public String getBasePath() {
    return basePath;
  }

@Override
public void initialise() throws InitialisationException {
	LOGGER.debug("Initializing config with name: " + configName);
	
}
  
}
