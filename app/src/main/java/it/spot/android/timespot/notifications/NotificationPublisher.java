package it.spot.android.timespot.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * @author a.rinaldi
 */
public class NotificationPublisher
        extends BroadcastReceiver {

    public static final String NOTIFICATION = "args_notification";
    public static final String NOTIFICATION_ID = "args_notification_id";

    // region BroadcastReceiver implementation

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }

    // endregion
}
