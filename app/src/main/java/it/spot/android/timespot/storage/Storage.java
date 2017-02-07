package it.spot.android.timespot.storage;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.domain.Organization;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public class Storage
        implements IStorage {

    private static final String PREFERENCES_NAME = "time_at_spot_preferences";
    private static final String PREFERENCES_ORGANIZATION_ID = "preferences_organization_id";

    private Context mContext;
    private SharedPreferences mPreferences;

    // region Construction

    public static IStorage init(Context context) {
        return new Storage(context);
    }

    private Storage(Context context) {
        super();
        mContext = context;
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // endregion

    // region IStorage implementation

    @Override
    public User getLoggedUser() {
        Realm realm = Realm.getDefaultInstance();
        Account account = TimeAuthenticatorHelper.getAccount(mContext);
        User user = realm.where(User.class).equalTo("_id", TimeAuthenticatorHelper.getUserId(mContext, account)).findFirst();
        realm.close();
        return user;
    }

    @Override
    public void setLoggedUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public String getCurrentOrganizationId() {
        return mPreferences.getString(PREFERENCES_ORGANIZATION_ID, "");
    }

    @Override
    public void setCurrentOrganizationId(String organizationId) {
        mPreferences.edit().putString(PREFERENCES_ORGANIZATION_ID, organizationId).commit();
    }

    @Override
    public List<Organization> getOrganizations() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Organization> organizations = realm.where(Organization.class).findAll();
        realm.close();
        return organizations;
    }

    @Override
    public Organization getOrganizationById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Organization organization = realm.where(Organization.class).equalTo("_id", id).findFirst();
        realm.close();
        return organization;
    }

    @Override
    public void setOrganizations(List<Organization> organizations) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(organizations);
        realm.commitTransaction();
        realm.close();
    }

    // endregion
}
