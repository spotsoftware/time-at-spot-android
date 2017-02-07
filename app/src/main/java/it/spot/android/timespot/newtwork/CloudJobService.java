package it.spot.android.timespot.newtwork;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * @author a.rinaldi
 */
public class CloudJobService
        extends JobService {

    @Override
    public boolean onStartJob(final JobParameters job) {

        Log.e("CloudJobService", "start " + job.getTag());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    jobFinished(job, false);
                    Log.e("CloudJobService", "completed " + job.getTag());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
