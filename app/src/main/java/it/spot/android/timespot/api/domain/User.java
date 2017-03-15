package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import it.spot.android.timespot.storage.Storage;

@Table(database = Storage.class)
public class User
        extends BaseModel
        implements Parcelable {

    @PrimaryKey
    private String _id;
    @Column
    private boolean active;
    @Column
    private boolean deleted;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String provider;
    @Column
    private String role;

//    private UserSettings userSettings;

    // region Construction

    public User() {
        super();
    }

    protected User(Parcel in) {
        _id = in.readString();
        active = in.readByte() != 0;
        deleted = in.readByte() != 0;
        name = in.readString();
        email = in.readString();
        provider = in.readString();
        role = in.readString();
//        userSettings = in.readParcelable(UserSettings.class.getClassLoader());
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public User set_id(String _id) {
        this._id = _id;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public User setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public User setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

//    public UserSettings getUserSettings() {
//        return userSettings;
//    }
//
//    public User setUserSettings(UserSettings userSettings) {
//        this.userSettings = userSettings;
//        return this;
//    }

    // endregion

    // region Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(provider);
        dest.writeString(role);
//        dest.writeParcelable(userSettings, flags);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // endregion
}
