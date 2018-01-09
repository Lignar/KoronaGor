package pl.edu.pwr.jlignarski.koronagor.model;

import android.graphics.Path;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.edu.pwr.jlignarski.koronagor.db.realm.StartingPointR;

/**
 * @author janusz on 09.12.17.
 */

public class StartingPoint {
    private final String name;
    private final double latitude;
    private final double longitude;
    private int reqTime;

    public StartingPoint(String name, double latitude, double longitude, int reqTime) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reqTime = reqTime;
    }

    public StartingPoint(StartingPointR startingPointR) {
        name = startingPointR.getName();
        latitude = startingPointR.getLatitude();
        longitude = startingPointR.getLongitude();
        reqTime = startingPointR.getReqTime();
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public String getRequiredTime() {
        return reqTime/60 + "h " + reqTime%60 + "m";
    }

    public MarkerOptionsWrapper buildMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(getLatLng());
        markerOptions.title(name);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        return new MarkerOptionsWrapper(markerOptions, true);
    }

    public void addPathPoint(Path path, MapInfo mapInfo) {
        path.lineTo((float)mapInfo.lngPosition(longitude), (float)mapInfo.latPosition(latitude));
    }

    public StartingPointR toRealm() {
        return new StartingPointR(name, latitude, longitude, reqTime);
    }
}
