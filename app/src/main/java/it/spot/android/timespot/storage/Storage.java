package it.spot.android.timespot.storage;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public class Storage
        implements IStorage {

    private static final String PREFERENCES_NAME = "time_at_spot_preferences";

    private Realm mRealm;
    private Context mContext;
    private SharedPreferences mPreferences;

    // region Construction

    public static IStorage init(Context context) {
        return new Storage(context);
    }

    private Storage(Context context) {
        super();
        mContext = context;
        mRealm = Realm.getDefaultInstance();
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // endregion

    // region IStorage implementation

    @Override
    public User getLoggedUser() {
        Account account = TimeAuthenticatorHelper.getAccount(mContext);
        return mRealm.where(User.class).equalTo("_id", TimeAuthenticatorHelper.getUserId(mContext, account)).findFirst();
    }

    @Override
    public void setLoggedUser(User user) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();
        mRealm.close();
    }

    @Override
    public String getCurrentOrganizationId() {
        return "54e3061b9f11ec0b0035107e";
    }

    @Override
    public void setCurrentOrganizationId(String organizationId) {

    }

    // endregion
}
