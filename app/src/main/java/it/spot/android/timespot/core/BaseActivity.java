package it.spot.android.timespot.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import it.spot.android.timespot.BuildConfig;

/**
 * @author a.rinaldi
 */
public abstract class BaseActivity
        extends AppCompatActivity
        implements IAnalyticsProvider {

    private FirebaseAnalytics mFirebaseAnalytics;

    // region Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        log(EventIds.NAVIGATION, getDescription());
    }

    // endregion

    // region Protected methods

    protected abstract String getDescription();

    // endregion

    // region IAnalyticsProvider implementation

    @Override
    public void log(String id, String name) {
        if (!BuildConfig.DEBUG) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }

    // endregion
}
