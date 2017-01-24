package it.spot.android.timespot.api.request;

/**
 * @author a.rinaldi
 */
public class AuthRequest {

    private String id_token;

    public AuthRequest() {
        super();
    }

    public String getId_token() {
        return id_token;
    }

    public AuthRequest setId_token(String id_token) {
        this.id_token = id_token;
        return this;
    }
}
