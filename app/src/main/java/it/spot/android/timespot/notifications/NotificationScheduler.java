package it.spot.android.timespot.notifications;

import android.app.PendingIntent;
import android.content.Context;

/**
 * @author a.rinaldi
 */
public abstract class NotificationScheduler
        implements INotificationScheduler {

    // region Construction

    protected NotificationScheduler() {
        super();
    }

    // endregion

    // region INotificationScheduler implementation

    @Override
    public abstract void schedule(Context context);

    @Override
    public abstract void cancel(Context context);

    // endregion

    // region Protected methods

    protected abstract PendingIntent getPendingIntent(Context context);

    // endregion
}
