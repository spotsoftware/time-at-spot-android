package it.spot.android.timespot.core;

/**
 * @author a.rinaldi
 */
public interface IAnalyticsProvider {

    class EventIds {
        public static final String NAVIGATION = "Navigation";
    }

    void log(String id, String name);
}
