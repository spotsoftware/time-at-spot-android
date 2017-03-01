package it.spot.android.timespot.storage.realm;

import io.realm.annotations.RealmModule;
import it.spot.android.timespot.api.domain.Client;
import it.spot.android.timespot.api.domain.Member;
import it.spot.android.timespot.api.domain.Organization;
import it.spot.android.timespot.api.domain.Project;
import it.spot.android.timespot.api.domain.User;
import it.spot.android.timespot.api.domain.WorkEntry;

/**
 * @author a.rinaldi
 */
@RealmModule(library = false, classes = {User.class, WorkEntry.class, Organization.class,
        Member.class, Project.class, Client.class})
public class ModulesRealm {
}
