package pl.edu.pwr.jlignarski.koronagor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

/**
 * @author janusz on 09.12.17.
 */

class PeakGoogleMapMvp implements MvpView {


    private final View rootView;
    private MapView googleMapView;

    public PeakGoogleMapMvp(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_peak_google_map, container, false);
        initialise();
    }

    private void initialise() {
        googleMapView = rootView.findViewById(R.id.googleMapView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public MapView getMap() {
        return googleMapView;
    }
}
