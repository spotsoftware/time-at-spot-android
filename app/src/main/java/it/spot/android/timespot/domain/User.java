package it.spot.android.timespot.domain;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author a.rinaldi
 */
public class User
        extends RealmObject
        implements Parcelable {

    @PrimaryKey
    private String _id;
    private String name;
    private String email;
    private String hashedPassword;
    private String provider;
    private String salt;
    private String deviceTokenHash;
    private String role;
    private String _lastOrganization;

    // region Construction

    public User() {
        super();
    }

    protected User(Parcel in) {
        _id = in.readString();
        name = in.readString();
        email = in.readString();
        hashedPassword = in.readString();
        provider = in.readString();
        salt = in.readString();
        deviceTokenHash = in.readString();
        role = in.readString();
        _lastOrganization = in.readString();
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public User setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public User setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getDeviceTokenHash() {
        return deviceTokenHash;
    }

    public User setDeviceTokenHash(String deviceTokenHash) {
        this.deviceTokenHash = deviceTokenHash;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public String get_lastOrganization() {
        return _lastOrganization;
    }

    public User set_lastOrganization(String _lastOrganization) {
        this._lastOrganization = _lastOrganization;
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
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(hashedPassword);
        dest.writeString(provider);
        dest.writeString(salt);
        dest.writeString(deviceTokenHash);
        dest.writeString(role);
        dest.writeString(_lastOrganization);
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
