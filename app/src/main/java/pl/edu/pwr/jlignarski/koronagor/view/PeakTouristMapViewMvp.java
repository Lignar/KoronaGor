package pl.edu.pwr.jlignarski.koronagor.view;

import android.location.Location;
import android.view.View;

import com.qozix.tileview.TileView;

import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.model.BitmapProviderInternalStorage;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 10.12.17.
 */

public class PeakTouristMapViewMvp implements MvpView {

    private final TileView rootView;
    private Peak peak;

    public PeakTouristMapViewMvp(Peak peak) {
        this.peak = peak;
        this.rootView = new TileView(App.getAppContext());
        rootView.setBitmapProvider(new BitmapProviderInternalStorage());
        peak.setMapSize(rootView);
        rootView.addDetailLevel(1f, peak, 128, 128);
        rootView.setScaleLimits(0f, 10f);
        peak.addMarkers(rootView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public View addLocationMarker(Location location) {
        return peak.addLocationMarker(rootView, location);
    }

    public void removeLocationMarker(View lastLocationMarker) {
        rootView.removeMarker(lastLocationMarker);
    }
}
