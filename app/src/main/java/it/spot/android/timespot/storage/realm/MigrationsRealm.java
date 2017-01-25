package it.spot.android.timespot.storage.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * @author a.rinaldi
 */
public class MigrationsRealm
        implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // INF: Empty
    }
}
