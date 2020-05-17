package com.company.akilovasi.ui.notification.callback;

import com.company.akilovasi.data.local.entities.Notification;

public interface NotificationItemOnClick {
    void onNotificationItemClick(Notification notification);
    void onNotificationDismissClick(Notification notification);
}
