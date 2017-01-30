package it.spot.android.timespot.api.response;

/**
 * @author a.rinaldi
 */
public class WorkEntryNewResponse {

    private String _id;
    private String _organization;
    private String _client;
    private String _performedBy;
    private String _project;
    private float amount;
    private String description;
    private String performedAt;

    public WorkEntryNewResponse() {
        super();
    }

    public String get_id() {
        return _id;
    }

    public WorkEntryNewResponse set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String get_organization() {
        return _organization;
    }

    public WorkEntryNewResponse set_organization(String _organization) {
        this._organization = _organization;
        return this;
    }

    public String get_client() {
        return _client;
    }

    public WorkEntryNewResponse set_client(String _client) {
        this._client = _client;
        return this;
    }

    public String get_performedBy() {
        return _performedBy;
    }

    public WorkEntryNewResponse set_performedBy(String _performedBy) {
        this._performedBy = _performedBy;
        return this;
    }

    public String get_project() {
        return _project;
    }

    public WorkEntryNewResponse set_project(String _project) {
        this._project = _project;
        return this;
    }

    public float getAmount() {
        return amount;
    }

    public WorkEntryNewResponse setAmount(float amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkEntryNewResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPerformedAt() {
        return performedAt;
    }

    public WorkEntryNewResponse setPerformedAt(String performedAt) {
        this.performedAt = performedAt;
        return this;
    }
}
