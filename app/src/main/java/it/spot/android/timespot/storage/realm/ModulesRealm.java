package it.spot.android.timespot.storage.realm;

import io.realm.annotations.RealmModule;
import it.spot.android.timespot.domain.Client;
import it.spot.android.timespot.domain.Member;
import it.spot.android.timespot.domain.Organization;
import it.spot.android.timespot.domain.Project;
import it.spot.android.timespot.domain.User;
import it.spot.android.timespot.domain.WorkEntry;

/**
 * @author a.rinaldi
 */
@RealmModule(library = false, classes = {User.class, WorkEntry.class, Organization.class,
        Member.class, Project.class, Client.class})
public class ModulesRealm {
}
