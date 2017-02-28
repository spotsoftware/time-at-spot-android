package it.spot.android.timespot.notifications;

import android.content.Context;

/**
 * @author a.rinaldi
 */
public interface INotificationScheduler {

    void schedule(Context context);

    void cancel(Context context);
}
