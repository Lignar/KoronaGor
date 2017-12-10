package pl.edu.pwr.jlignarski.koronagor;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author janusz on 08.12.17.
 */

class Peak {

    private String name;
    private int height;
    private String range;
    private final double latitude;
    private final double longitude;
    private List<StartingPoint> startingPoints;

    public Peak(String name, int height, String range, double latitude, double longitude, List<StartingPoint> startingPoints) {
        this.name = name;
        this.height = height;
        this.range = range;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startingPoints = startingPoints;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public String getRange() {
        return range;
    }

    public String getId() {
        return name;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public List<StartingPoint> getStartingPoints() {
        return startingPoints;
    }

    public List<MarkerOptionsWrapper> getMapMarkers() {
        List<MarkerOptionsWrapper> result = new ArrayList<>();
        result.add(buildMarker());
        for (StartingPoint startingPoint : startingPoints) {
            result.add(startingPoint.buildMarker());
        }
        return result;
    }

    private MarkerOptionsWrapper buildMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(getLatLng());
        return new MarkerOptionsWrapper(markerOptions, false);
    }
}
