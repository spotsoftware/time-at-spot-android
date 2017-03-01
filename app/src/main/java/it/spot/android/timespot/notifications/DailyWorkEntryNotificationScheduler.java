package it.spot.android.timespot.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import it.spot.android.timespot.R;
import it.spot.android.timespot.workentry.WorkEntryNewActivity;

/**
 * @author a.rinaldi
 */
public class DailyWorkEntryNotificationScheduler
        extends NotificationScheduler {

    // region Construction

    public static NotificationScheduler newInstance() {
        return new DailyWorkEntryNotificationScheduler();
    }

    protected DailyWorkEntryNotificationScheduler() {
        super();
    }

    // endregion

    // region NotificationScheduler implementation

    @Override
    protected PendingIntent getPendingIntent(Context context) {
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(context));
        return PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void schedule(Context context) {

        Calendar firingCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR_OF_DAY, 19); // At the hour you wanna fire
        firingCal.set(Calendar.MINUTE, 0); // Particular minute
        firingCal.set(Calendar.SECOND, 0); // particular second

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (intendedTime >= currentTime) {
            // you can add buffer time too here to ignore some small differences in milliseconds
            // set from today
            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        } else {
            // set from next day
            // you might consider using calendar.add() for adding one day to the current day
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();

            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        }
    }

    @Override
    public void cancel(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    // endregion

    // region Private methods

    private Notification getNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(context.getString(R.string.daily_work_entry_reminder));
        Intent intent = new Intent(context, WorkEntryNewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(PendingIntent.getActivity(context, WorkEntryNewActivity.NEW_WORK_ENTRY_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    // endregion
}
