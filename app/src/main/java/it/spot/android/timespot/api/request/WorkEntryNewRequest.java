package it.spot.android.timespot.api.request;

/**
 * @author a.rinaldi
 */
public class WorkEntryNewRequest {

    private String _client;
    private String _performedBy;
    private String _project;
    private float amount;
    private String description;
    private String performedAt;

    public WorkEntryNewRequest() {
        super();
    }

    public String get_client() {
        return _client;
    }

    public WorkEntryNewRequest set_client(String _client) {
        this._client = _client;
        return this;
    }

    public String get_performedBy() {
        return _performedBy;
    }

    public WorkEntryNewRequest set_performedBy(String _performedBy) {
        this._performedBy = _performedBy;
        return this;
    }

    public String get_project() {
        return _project;
    }

    public WorkEntryNewRequest set_project(String _project) {
        this._project = _project;
        return this;
    }

    public float getAmount() {
        return amount;
    }

    public WorkEntryNewRequest setAmount(float amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkEntryNewRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPerformedAt() {
        return performedAt;
    }

    public WorkEntryNewRequest setPerformedAt(String performedAt) {
        this.performedAt = performedAt;
        return this;
    }
}
