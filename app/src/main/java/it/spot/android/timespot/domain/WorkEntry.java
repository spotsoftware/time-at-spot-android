package it.spot.android.timespot.domain;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author a.rinaldi
 */
public class WorkEntry
        extends RealmObject
        implements Parcelable {

    @PrimaryKey
    private String _id;
    private boolean active;
    private boolean deleted;
    private String description;
    private double amount;

    // region Construction

    public WorkEntry() {
        super();
    }

    protected WorkEntry(Parcel in) {
        _id = in.readString();
        active = in.readByte() != 0;
        deleted = in.readByte() != 0;
        description = in.readString();
        amount = in.readDouble();
    }

    // endregion

    // region Public methods

    public String get_id() {
        return _id;
    }

    public WorkEntry set_id(String _id) {
        this._id = _id;
        return this;
    }

    public boolean getActive() {
        return active;
    }

    public WorkEntry setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public WorkEntry setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public WorkEntry setAmount(double amount) {
        this.amount = amount;
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
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeString(description);
        dest.writeDouble(amount);
    }

    public static final Creator<WorkEntry> CREATOR = new Creator<WorkEntry>() {
        @Override
        public WorkEntry createFromParcel(Parcel in) {
            return new WorkEntry(in);
        }

        @Override
        public WorkEntry[] newArray(int size) {
            return new WorkEntry[size];
        }
    };

    // endregion
}
