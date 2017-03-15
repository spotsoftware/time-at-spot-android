package it.spot.android.timespot;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author a.rinaldi
 */
public class Application
        extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase(this);
    }

    private static void initDatabase(Context context) {
        FlowManager.init(new FlowConfig.Builder(context).build());
    }
}
