package it.spot.android.timespot.storage.realm;

import io.realm.annotations.RealmModule;
import it.spot.android.timespot.domain.User;
import it.spot.android.timespot.domain.WorkEntry;

/**
 * @author a.rinaldi
 */
@RealmModule(library = false, classes = {User.class, WorkEntry.class})
public class ModulesRealm {
}
