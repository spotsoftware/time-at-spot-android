package it.spot.android.timespot.storage;

import java.util.List;

import it.spot.android.timespot.api.domain.Client;
import it.spot.android.timespot.api.domain.Organization;
import it.spot.android.timespot.api.domain.Project;
import it.spot.android.timespot.api.domain.User;

/**
 * @author a.rinaldi
 */
public interface IStorage {

    void clear();

    User getLoggedUser();

    String getLoggedUserId();

    void setLoggedUser(User user);

    String getCurrentOrganizationId();

    void setCurrentOrganizationId(String organizationId);

    List<Organization> getOrganizations();

    Organization getOrganizationById(String id);

    void setOrganizations(List<Organization> organizations);

    List<Client> getClients();

    Client getClient(String id);

    void setClients(List<Client> clients);

    List<Project> getProjects();

    List<Project> getActiveProjects();

    Project getProject(String id);

    void setProjects(List<Project> projects);
}
