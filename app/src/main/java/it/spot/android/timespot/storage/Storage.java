package it.spot.android.timespot.storage;

import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public class Storage
        implements IStorage {

    private static final String PREFERENCES_NAME = "time_at_spot_preferences";

    private Realm mRealm;
    private SharedPreferences mPreferences;

    // region Construction

    public static IStorage init(Context context) {
        return new Storage(context);
    }

    private Storage(Context context) {
        super();
        mRealm = Realm.getDefaultInstance();
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // endregion

    // region IStorage implementation

    @Override
    public User getLoggedUser() {
        return null;
    }

    @Override
    public void setLoggedUser(User user) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(user);
        mRealm.close();
    }

    // endregion
}
