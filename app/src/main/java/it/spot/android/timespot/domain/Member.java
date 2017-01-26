package it.spot.android.timespot.domain;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author a.rinaldi
 */
public class Member
        extends RealmObject
        implements Parcelable {

    @PrimaryKey
    private String _id;
    private String user;
    private String role;

    // region Construction

    public Member() {
        super();
    }

    protected Member(Parcel in) {
        _id = in.readString();
        user = in.readString();
        role = in.readString();
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public Member set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Member setUser(String user) {
        this.user = user;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Member setRole(String role) {
        this.role = role;
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
        dest.writeString(user);
        dest.writeString(role);
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    // endregion
}
