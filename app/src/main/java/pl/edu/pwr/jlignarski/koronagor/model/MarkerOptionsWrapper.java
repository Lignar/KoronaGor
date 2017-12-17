package pl.edu.pwr.jlignarski.koronagor.model;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author janusz on 10.12.17.
 */

public class MarkerOptionsWrapper {

    private MarkerOptions markerOptions;
    private boolean isDestination;

    public MarkerOptionsWrapper(MarkerOptions markerOptions, boolean isDestination) {

        this.markerOptions = markerOptions;
        this.isDestination = isDestination;
    }

    public void addToMap(GoogleMap map) {
        Marker marker = map.addMarker(markerOptions);
        if (isDestination) {
            marker.setTag(markerOptions.getPosition());
        }
    }
}
