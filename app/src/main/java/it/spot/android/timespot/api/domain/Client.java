package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import it.spot.android.timespot.storage.Storage;

@Table(database = Storage.class)
public class Client
        extends BaseModel
        implements Parcelable {

    @PrimaryKey
    private String _id;
    @Column
    private String initials;
    @Column
    private String icon;
    @Column
    private String name;
    @Column
    private boolean active;
    @Column
    private boolean deleted;

    // region Construction

    public Client() {
        super();
    }

    protected Client(Parcel in) {
        _id = in.readString();
        initials = in.readString();
        icon = in.readString();
        name = in.readString();
        active = in.readByte() != 0;
        deleted = in.readByte() != 0;
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public Client set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getInitials() {
        return initials;
    }

    public Client setInitials(String initials) {
        this.initials = initials;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Client setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Client setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Client setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        dest.writeString(initials);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    // endregion
}
