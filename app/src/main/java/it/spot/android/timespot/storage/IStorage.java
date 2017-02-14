package it.spot.android.timespot.storage;

import java.util.List;

import it.spot.android.timespot.domain.Client;
import it.spot.android.timespot.domain.Organization;
import it.spot.android.timespot.domain.Project;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
public interface IStorage {

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
