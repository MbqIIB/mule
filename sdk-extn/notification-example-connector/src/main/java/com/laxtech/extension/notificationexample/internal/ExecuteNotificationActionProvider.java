package com.laxtech.extension.notificationexample.internal;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.notification.NotificationActionProvider;
import org.mule.runtime.extension.api.notification.NotificationActionDefinition;

import com.laxtech.extension.notificationexample.api.SimpleNotificationAction;

public class ExecuteNotificationActionProvider implements NotificationActionProvider {

	@Override
	public Set<NotificationActionDefinition> getNotificationActions() {
		
		HashSet<NotificationActionDefinition> actions = new HashSet<>();
        actions.add(SimpleNotificationAction.NEW_EVENT);
        actions.add(SimpleNotificationAction.FINISHED_EVENT);
        return actions;
        
/*        return ImmutableSet.<NotificationActionDefinition>builder()
                .add(SimpleNotificationAction.NEW_EVENT)
                .add(SimpleNotificationAction.FINISHED_EVENT)
        .build();*/
	}

}
