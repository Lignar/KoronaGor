package pl.edu.pwr.jlignarski.koronagor;

import com.google.android.gms.location.LocationListener;

/**
 * @author janusz on 10.12.17.
 */

interface LocationObserverManager {
    void registerLocationObserver(LocationListener listener);

    void unregisterLocationObserver(LocationListener listener);
}
