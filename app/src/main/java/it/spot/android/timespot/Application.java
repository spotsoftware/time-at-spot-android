package it.spot.android.timespot;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import it.spot.android.timespot.storage.realm.MigrationsRealm;
import it.spot.android.timespot.storage.realm.ModulesRealm;

/**
 * @author a.rinaldi
 */
public class Application
        extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm(this);
    }

    private static void initRealm(Context context) {
        Realm.init(context);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded() // this line should be deleted in a production scenario
                .migration(new MigrationsRealm())
                .modules(new ModulesRealm())
                .build());
    }
}
