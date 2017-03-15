package it.spot.android.timespot.storage;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import it.spot.android.timespot.api.domain.Client;
import it.spot.android.timespot.api.domain.Client_Table;
import it.spot.android.timespot.api.domain.Organization;
import it.spot.android.timespot.api.domain.Organization_Table;
import it.spot.android.timespot.api.domain.Project;
import it.spot.android.timespot.api.domain.Project_Table;
import it.spot.android.timespot.api.domain.User;
import it.spot.android.timespot.api.domain.User_Table;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;

@Database(name = Storage.NAME, version = Storage.VERSION,
        consistencyCheckEnabled = true, foreignKeyConstraintsEnforced = true)
public class Storage
        implements IStorage {

    public static final String NAME = "TimeAtSpotDb";
    public static final int VERSION = 1;

    private static final String PREFERENCES_NAME = "time_at_spot_preferences";
    private static final String PREFERENCES_ORGANIZATION_ID = "preferences_organization_id";

    private Context mContext;
    private SharedPreferences mPreferences;

    // region Construction

    public static IStorage init(Context context) {
        return new Storage(context);
    }

    private Storage(Context context) {
        super();
        mContext = context;
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // endregion

    // region IStorage implementation

    @Override
    public void clear() {
        mPreferences.edit().clear().commit();

//        Delete.
//                Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();
//        realm.close();
    }

    @Override
    public User getLoggedUser() {
        Account account = TimeAuthenticatorHelper.getAccount(mContext);
        return SQLite.select()
                .from(User.class)
                .where(User_Table._id.is(TimeAuthenticatorHelper.getUserId(mContext, account)))
                .querySingle();
    }

    @Override
    public String getLoggedUserId() {
        User user = getLoggedUser();
        if (user != null) {
            return user.get_id();

        } else {
            return null;
        }
    }

    @Override
    public void setLoggedUser(final User user) {
        FlowManager.getDatabase(Storage.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                user.save(databaseWrapper);
            }
        });
    }

    @Override
    public String getCurrentOrganizationId() {
        return mPreferences.getString(PREFERENCES_ORGANIZATION_ID, "");
    }

    @Override
    public void setCurrentOrganizationId(String organizationId) {
        mPreferences.edit().putString(PREFERENCES_ORGANIZATION_ID, organizationId).commit();
    }

    @Override
    public List<Organization> getOrganizations() {
        return SQLite.select()
                .from(Organization.class)
                .where(Organization_Table.active.is(true))
                .queryList();
    }

    @Override
    public Organization getOrganizationById(String id) {
        return SQLite.select()
                .from(Organization.class)
                .where(Organization_Table._id.is(id))
                .querySingle();
    }

    @Override
    public void setOrganizations(final List<Organization> organizations) {
        DatabaseDefinition database = FlowManager.getDatabase(Storage.class);
        Transaction transaction = database.beginTransactionAsync(FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(Organization.class))
                .addAll(organizations).build()).build();
        transaction.execute();
    }

    @Override
    public List<Project> getProjects() {
        return SQLite.select()
                .from(Project.class)
                .queryList();
    }

    @Override
    public List<Project> getActiveProjects() {
        return SQLite.select()
                .from(Project.class)
                .where(Project_Table.active.is(true))
                .queryList();
    }

    @Override
    public Project getProject(String id) {
        return SQLite.select()
                .from(Project.class)
                .where(Project_Table._id.is(id))
                .querySingle();
    }

    @Override
    public void setProjects(final List<Project> projects) {
        DatabaseDefinition database = FlowManager.getDatabase(Storage.class);
        Transaction transaction = database.beginTransactionAsync(FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(Project.class))
                .addAll(projects).build()).build();
        transaction.execute();
    }

    @Override
    public List<Client> getClients() {
        return SQLite.select()
                .from(Client.class)
                .queryList();
    }

    @Override
    public List<Client> getActiveClients() {
        return SQLite.select()
                .from(Client.class)
                .where(Client_Table.active.is(true))
                .queryList();
    }

    @Override
    public Client getClient(String id) {
        return SQLite.select()
                .from(Client.class)
                .where(Client_Table._id.is(id))
                .querySingle();
    }

    @Override
    public void setClients(final List<Client> clients) {
        DatabaseDefinition database = FlowManager.getDatabase(Storage.class);
        Transaction transaction = database.beginTransactionAsync(FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(Client.class))
                .addAll(clients).build()).build();
        transaction.execute();
    }

    // endregion
}
