package it.spot.android.timespot.newtwork;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;

/**
 * @author a.rinaldi
 */
public class CloudHelper {

    public static CloudHelper getInstance(Context context) {
        return new CloudHelper(context);
    }

    private Context mContext;
    private FirebaseJobDispatcher mDispatcher;

    public CloudHelper(Context context) {
        super();
        mContext = context;
        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mContext));
    }

    public void execute(String action, Bundle extras) {
        Job job = mDispatcher.newJobBuilder()
                .setService(CloudJobService.class)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(0, 1))
                .setExtras(extras)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTag(action)
                .build();

        mDispatcher.mustSchedule(job);
    }

    public void execute1(String action, Bundle extras) {
        Job job = mDispatcher.newJobBuilder()
                .setService(CloudJobService.class)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 1))
                .setExtras(extras)
                .setTag(action)
                .build();

        mDispatcher.mustSchedule(job);
    }
}
