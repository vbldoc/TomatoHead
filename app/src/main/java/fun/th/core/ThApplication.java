package fun.th.core;

import android.app.Application;
import org.matomo.sdk.Matomo;
import org.matomo.sdk.Tracker;
import org.matomo.sdk.TrackerBuilder;

public class ThApplication extends Application {
    private Tracker mMatomoTracker;

    public synchronized Tracker getTracker() {
        if (mMatomoTracker != null) return mMatomoTracker;
        mMatomoTracker = TrackerBuilder.createDefault("https://th.matomo.cloud/matomo.php", 1).build((Matomo.getInstance(this)));
        return mMatomoTracker;
    }
}