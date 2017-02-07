package it.spot.android.timespot.storage;

import java.util.List;

import it.spot.android.timespot.domain.Organization;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public interface IStorage {

    User getLoggedUser();

    void setLoggedUser(User user);

    String getCurrentOrganizationId();

    void setCurrentOrganizationId(String organizationId);

    List<Organization> getOrganizations();

    Organization getOrganizationById(String id);

    void setOrganizations(List<Organization> organizations);
}
