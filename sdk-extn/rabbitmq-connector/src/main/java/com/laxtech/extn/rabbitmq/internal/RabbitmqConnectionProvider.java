package com.laxtech.extn.rabbitmq.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class RabbitmqConnectionProvider implements PoolingConnectionProvider<RabbitmqConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(RabbitmqConnectionProvider.class);


  @DisplayName("Host Name")
  @Parameter
  @Optional(defaultValue = "localhost")
  private String hostname;

  @DisplayName("Queue Name")
  @Parameter
  @Optional(defaultValue = "muleAppQueue")
  private String queueName;

  @Override
  public RabbitmqConnection connect() throws ConnectionException {
    return new RabbitmqConnection(hostname, queueName);
  }

  @Override
  public void disconnect(RabbitmqConnection connection) {
    try {
      //connection.invalidate();
      connection.disconnect();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(RabbitmqConnection connection) {
	  ConnectionValidationResult result;
	  if(connection.isConnected()){
		  result = ConnectionValidationResult.success();
	  } else {
		  result = ConnectionValidationResult.failure("Connection failed " + connection.getId(), new Exception());
	  }

    return result;
  }
  
}
