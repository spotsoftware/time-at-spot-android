package it.spot.android.timespot.storage.realm;

import io.realm.annotations.RealmModule;
import it.spot.android.timespot.domain.User;

/**
 * @author a.rinaldi
 */
@RealmModule(library = false, classes = {User.class})
public class ModulesRealm {
}
