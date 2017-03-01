package it.spot.android.timespot.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @author a.rinaldi
 */
public abstract class BaseFragment
        extends Fragment {

    private IAnalyticsProvider mAnalyticsProvider;

    // region Fragment life cycle

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IAnalyticsProvider) {
            mAnalyticsProvider = (IAnalyticsProvider) context;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        if (childFragment instanceof BaseFragment) {
            if (mAnalyticsProvider != null) {
                ((BaseFragment) childFragment).setAnalyticsProvider(mAnalyticsProvider);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mAnalyticsProvider != null) {
            mAnalyticsProvider.log(IAnalyticsProvider.EventIds.NAVIGATION, getDescription());
        }
    }

    // endregion

    // region Public methods

    public void setAnalyticsProvider(IAnalyticsProvider analyticsProvider) {
        mAnalyticsProvider = analyticsProvider;
    }

    // endregion

    // region Protected methods

    protected abstract String getDescription();

    // endregion
}
