package pl.edu.pwr.jlignarski.koronagor.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import pl.edu.pwr.jlignarski.koronagor.R;

/**
 * @author janusz on 09.12.17.
 */

public class PeakGoogleMapViewMvp implements MvpView {


    private final View rootView;
    private MapView googleMapView;

    public PeakGoogleMapViewMvp(LayoutInflater inflater, ViewGroup container) {
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
