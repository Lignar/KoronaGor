package pl.edu.pwr.jlignarski.koronagor;

import android.location.Location;
import android.view.View;

import com.qozix.tileview.TileView;

/**
 * @author janusz on 10.12.17.
 */

class PeakTouristMapMvp implements MvpView {

    private final TileView rootView;
    private Peak peak;

    public PeakTouristMapMvp(Peak peak) {
        this.peak = peak;
        this.rootView = new TileView(App.getAppContext());
        peak.setMapSize(rootView);
        rootView.addDetailLevel(1f, peak.getMapRegex(), 128, 128);
        rootView.setScaleLimits(0f, 10f);
        peak.addMarkers(rootView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void addLocationMarker(Location location) {
        peak.addLocationMarker(rootView, location);
    }
}
