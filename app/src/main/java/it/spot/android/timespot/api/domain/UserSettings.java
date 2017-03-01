package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * @author a.rinaldi
 */
public class UserSettings
        extends RealmObject
        implements Parcelable {

    private String defaultOrganization;

    // region Construction

    public UserSettings() {
        super();
    }

    protected UserSettings(Parcel in) {
        defaultOrganization = in.readString();
    }

    // endregion

    // region Public methods

    public String getDefaultOrganization() {
        return defaultOrganization;
    }

    public UserSettings setDefaultOrganization(String defaultOrganization) {
        this.defaultOrganization = defaultOrganization;
        return this;
    }

    // endregion

    // region Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(defaultOrganization);
    }

    public static final Creator<UserSettings> CREATOR = new Creator<UserSettings>() {
        @Override
        public UserSettings createFromParcel(Parcel in) {
            return new UserSettings(in);
        }

        @Override
        public UserSettings[] newArray(int size) {
            return new UserSettings[size];
        }
    };

    // endregion
}
