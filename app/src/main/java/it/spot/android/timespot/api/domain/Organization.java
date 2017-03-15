package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import it.spot.android.timespot.storage.Storage;

@Table(database = Storage.class)
public class Organization
        extends BaseModel
        implements Parcelable {

    @PrimaryKey
    private String _id;
    @Column
    private String name;
    @Column
    private String lastImport;
    @Column
    private boolean active;
    @Column
    private boolean deleted;
//    private RealmList<Member> members;

    // region Construction

    public Organization() {
        super();
    }

    protected Organization(Parcel in) {
        _id = in.readString();
        name = in.readString();
        lastImport = in.readString();
        active = in.readByte() != 0;
        deleted = in.readByte() != 0;

//        members = new RealmList<>();
//        in.readTypedList(members, Member.CREATOR);
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public Organization set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getLastImport() {
        return lastImport;
    }

    public Organization setLastImport(String lastImport) {
        this.lastImport = lastImport;
        return this;
    }

    public String getName() {
        return name;
    }

    public Organization setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Organization setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Organization setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

//    public List<Member> getMembers() {
//        return members;
//    }
//
//    public Organization setMembers(List<Member> members) {
//        this.members = new RealmList<>();
//        this.members.addAll(members);
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
        dest.writeString(name);
        dest.writeString(lastImport);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
//        dest.writeTypedList(members);
    }

    public static final Creator<Organization> CREATOR = new Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel in) {
            return new Organization(in);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };

    // endregion
}
