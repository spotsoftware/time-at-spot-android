package it.spot.android.timespot.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author a.rinaldi
 */
public class TimeAuthenticatorService
        extends Service {

    // region Service implementation

    @Override
    public IBinder onBind(Intent intent) {
        TimeAuthenticator authenticator = new TimeAuthenticator(this);
        return authenticator.getIBinder();
    }

    // endregion
}