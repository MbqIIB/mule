package com.laxtech.extension.notificationexample.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.notification.Fires;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.notification.NotificationActionDefinition;
import org.mule.runtime.extension.api.notification.NotificationEmitter;

import com.laxtech.extension.notificationexample.api.SimpleNotificationDataObject;
import static com.laxtech.extension.notificationexample.api.SimpleNotificationAction.NEW_EVENT;
import static com.laxtech.extension.notificationexample.api.SimpleNotificationAction.FINISHED_EVENT;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class NotificationexampleOperations {

	/**
	 * Example of an operation that uses the configuration and a connection instance
	 * to perform some action.
	 */
	@MediaType(value = ANY, strict = false)
	public String retrieveInfo(@Config NotificationexampleConfiguration configuration,
			@Connection NotificationexampleConnection connection) {
		return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId()
				+ "]";
	}

	/**
   * Example of a simple operation that receives a string parameter and returns a new string message that will be set on the payload.
   */
  @MediaType(value = ANY, strict = false)
  @Fires(ExecuteNotificationActionProvider.class)
  public String sayHi(String person, NotificationEmitter notificationEmitter) {
	  notificationEmitter.fire(NEW_EVENT, TypedValue.of(4));
	  String greeting = buildHelloMessage(person);
	  notificationEmitter.fire(FINISHED_EVENT, 
			  TypedValue.of(new SimpleNotificationDataObject("Notification 0999: " + person)));
	  return greeting;
  }

	/**
	 * Private Methods are not exposed as operations
	 */
	private String buildHelloMessage(String person) {
		return "Hello " + person + "!!!";
	}
}
