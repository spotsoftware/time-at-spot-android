package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author a.rinaldi
 */
public class Project
        extends RealmObject
        implements Parcelable {

    @PrimaryKey
    private String _id;
//    private String _client;
//    private String _organization;
    private String name;
    private float amount;
    private String icon;
    private String initials;
    private String group;
    private boolean active;

    // region Construction

    public Project() {
        super();
    }

    protected Project(Parcel in) {
        _id = in.readString();
//        _client = in.readString();
//        _organization = in.readString();
        name = in.readString();
        amount = in.readFloat();
        icon = in.readString();
        initials = in.readString();
        group = in.readString();
        active = in.readByte() != 0;
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public Project set_id(String _id) {
        this._id = _id;
        return this;
    }

//    public String get_organization() {
//        return _organization;
//    }
//
//    public Project set_organization(String _organization) {
//        this._organization = _organization;
//        return this;
//    }
//
//    public String get_client() {
//        return _client;
//    }
//
//    public Project set_client(String _client) {
//        this._client = _client;
//        return this;
//    }

    public String getInitials() {
        return initials;
    }

    public Project setInitials(String initials) {
        this.initials = initials;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Project setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public Project setGroup(String group) {
        this.group = group;
        return this;
    }

    public float getAmount() {
        return amount;
    }

    public Project setAmount(float amount) {
        this.amount = amount;
        return this;
    }

    public boolean getActive() {
        return active;
    }

    public Project setActive(boolean active) {
        this.active = active;
        return this;
    }

//    public boolean getDeleted() {
//        return deleted;
//    }
//
//    public Project setDeleted(boolean deleted) {
//        this.deleted = deleted;
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
//        dest.writeString(_client);
//        dest.writeString(_organization);
        dest.writeString(name);
        dest.writeFloat(amount);
        dest.writeString(icon);
        dest.writeString(initials);
        dest.writeString(group);
        dest.writeByte((byte) (active ? 1 : 0));
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    // endregion
}
