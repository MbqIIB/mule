package com.laxtech.connectors.calculator.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;

import javax.inject.Inject;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class (as it's name implies) provides connection instances and the
 * funcionality to disconnect and validate those connections.
 * <p>
 * All connection related parameters (values required in order to create a
 * connection) must be declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares
 * that connections resolved by this provider will be pooled and reused. There
 * are other implementations like {@link CachedConnectionProvider} which lazily
 * creates and caches connections or simply {@link ConnectionProvider} if you
 * want a new connection each time something requires one.
 */
public class CalculatorConnectionProvider implements CachedConnectionProvider<CalculatorConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(CalculatorConnectionProvider.class);

	@Parameter
	@Placement(tab = "Advanced")
	@Optional(defaultValue = "5000")
	int connectionTimeout;

	@ParameterGroup(name = "Proxy Config")
	CalculatorProxyConfig proxyConfig;
	
    @Inject
    HttpService httpService;

    @Parameter
    private String token;
    

	@Override
	public void disconnect(CalculatorConnection connection) {
		connection.disconnect();
	}
	
    @Override
    public CalculatorConnection connect() throws ConnectionException {
        return new CalculatorConnection(token, connectionTimeout, httpService, proxyConfig.getProxyConfig().orElse(null));
}

	@Override
	public ConnectionValidationResult validate(CalculatorConnection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
