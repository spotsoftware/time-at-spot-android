package it.spot.android.timespot;

import android.app.IntentService;
import android.content.Intent;

import it.spot.android.timespot.api.TimeEndpoint;

/**
 * @author a.rinaldi
 */
public class StartupService
        extends IntentService {

    // region Construction

    public StartupService(String name) {
        super(name);
    }

    // endregion

    // region IntentService implementation

    @Override
    protected void onHandleIntent(Intent intent) {
//        TimeEndpoint.getInstance(getApplicationContext()).create()
    }

    // endregion
}
