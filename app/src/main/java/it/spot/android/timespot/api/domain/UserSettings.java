package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import it.spot.android.timespot.storage.Storage;

@Table(database = Storage.class)
public class UserSettings
        extends BaseModel
        implements Parcelable {

    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private String defaultOrganization;

    // region Construction

    public UserSettings() {
        super();
    }

    protected UserSettings(Parcel in) {
        id = in.readLong();
        defaultOrganization = in.readString();
    }

    // endregion

    // region Public methods

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        dest.writeLong(id);
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
