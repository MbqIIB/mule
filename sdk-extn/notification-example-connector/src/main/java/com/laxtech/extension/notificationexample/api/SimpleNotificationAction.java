/**
 * 
 */
package com.laxtech.extension.notificationexample.api;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.extension.api.notification.NotificationActionDefinition;

/**
 * @author sanjeev
 *
 */
public enum SimpleNotificationAction implements NotificationActionDefinition<SimpleNotificationAction> {

	NEW_EVENT(DataType.fromType(Integer.class)),

	FINISHED_EVENT(DataType.fromType(SimpleNotificationDataObject.class));

	private final DataType dataType;

	SimpleNotificationAction(DataType dataType) {
		this.dataType = dataType;
	}

	@Override
	public DataType getDataType() {
		return dataType;
	}

}
