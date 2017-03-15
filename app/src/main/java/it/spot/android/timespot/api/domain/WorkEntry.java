package it.spot.android.timespot.api.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import it.spot.android.timespot.storage.Storage;

@Table(database = Storage.class)
public class WorkEntry
        extends BaseModel
        implements Parcelable {

    @PrimaryKey
    private String _id;
    @Column
    private boolean active;
    @Column
    private boolean deleted;
    @Column
    private String description;
    @Column
    private String performedAt;
    @Column
    private double amount;

    @Column
    @ForeignKey(stubbedRelationship = true)
    private Client _client;
    @Column
    @ForeignKey(stubbedRelationship = true)
    private Project _project;
    @Column
    @ForeignKey(stubbedRelationship = true)
    private User _performedBy;
    @Column
    @ForeignKey(stubbedRelationship = true)
    private Organization _organization;

    // region Construction

    public WorkEntry() {
        super();
    }

    protected WorkEntry(Parcel in) {
        _id = in.readString();
        active = in.readByte() != 0;
        deleted = in.readByte() != 0;
        description = in.readString();
        performedAt = in.readString();
        amount = in.readDouble();
        _performedBy = in.readParcelable(User.class.getClassLoader());
        _client = in.readParcelable(Client.class.getClassLoader());
        _project = in.readParcelable(Project.class.getClassLoader());
        _organization = in.readParcelable(Organization.class.getClassLoader());
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

    public boolean isActive() {
        return active;
    }

    public WorkEntry setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isDeleted() {
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

    public String getPerformedAt() {
        return performedAt;
    }

    public WorkEntry setPerformedAt(String performedAt) {
        this.performedAt = performedAt;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public WorkEntry setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Client get_client() {
        return _client;
    }

    public WorkEntry set_client(Client _client) {
        this._client = _client;
        return this;
    }

    public User get_performedBy() {
        return _performedBy;
    }

    public WorkEntry set_performedBy(User _performedBy) {
        this._performedBy = _performedBy;
        return this;
    }

    public Project get_project() {
        return _project;
    }

    public WorkEntry set_project(Project _project) {
        this._project = _project;
        return this;
    }

    public Organization get_organization() {
        return _organization;
    }

    public WorkEntry set_organization(Organization _organization) {
        this._organization = _organization;
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
        dest.writeString(performedAt);
        dest.writeDouble(amount);
        dest.writeParcelable(_client, flags);
        dest.writeParcelable(_project, flags);
        dest.writeParcelable(_performedBy, flags);
        dest.writeParcelable(_organization, flags);
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
