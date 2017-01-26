package it.spot.android.timespot.api.request;

/**
 * @author a.rinaldi
 */
public class WorkEntriesRequest {

    private String userId;
    private String from;
    private String to;

    public WorkEntriesRequest() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public WorkEntriesRequest setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public WorkEntriesRequest setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public WorkEntriesRequest setTo(String to) {
        this.to = to;
        return this;
    }
}
