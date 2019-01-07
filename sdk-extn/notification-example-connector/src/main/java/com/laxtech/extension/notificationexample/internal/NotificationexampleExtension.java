package com.laxtech.extension.notificationexample.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Export;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.notification.NotificationActions;

import com.laxtech.extension.notificationexample.api.SimpleNotificationAction;
import com.laxtech.extension.notificationexample.api.SimpleNotificationDataObject;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "notification-example")
@Extension(name = "Notification example")
@Configurations(NotificationexampleConfiguration.class)
@NotificationActions(SimpleNotificationAction.class)
@Export(classes = {SimpleNotificationDataObject.class})
public class NotificationexampleExtension {

}
