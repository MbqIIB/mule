package com.laxtech.connector.multipleconf.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

import com.laxtech.connector.multipleconf.internal.listener.MConfHttpListenerConfig;
import com.laxtech.connector.multipleconf.internal.request.MConfHttpRequesterConfig;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "multiple-configuration")
@Extension(name = "Multiple Configuration HTTP")
@Configurations({MConfHttpListenerConfig.class, MConfHttpRequesterConfig.class})
public class MultipleConfigurationExtension {

}
