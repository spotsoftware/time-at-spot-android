package it.spot.android.timespot.storage;

import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public interface IStorage {

    User getLoggedUser();

    void setLoggedUser(User user);

    String getCurrentOrganizationId() ;

    void setCurrentOrganizationId(String organizationId);
}
