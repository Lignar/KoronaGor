package pl.edu.pwr.jlignarski.koronagor;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qozix.tileview.TileView;

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
    private MapInfo mapInfo;

    public Peak(String name, int height, String range, double latitude, double longitude, List<StartingPoint> startingPoints,
                String mapRegex, int width, int mapHeight, double xs, double xe, double ys, double ye) {
        this.name = name;
        this.height = height;
        this.range = range;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startingPoints = startingPoints;
        this.mapInfo = new MapInfo(mapRegex, width, mapHeight, xs, xe, ys, ye);
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

    public String getMapRegex() {
        return mapInfo.getRegex();
    }

    public void setMapSize(TileView rootView) {
        mapInfo.setMapSize(rootView);
    }

    public void addMarkers(TileView rootView) {
        ImageView imageView = new ImageView(App.getAppContext());
        Drawable drawable = App.getAppContext().getDrawable(R.drawable.marker);
        imageView.setImageDrawable(drawable);
        rootView.addMarker(imageView, mapInfo.lngPosition(longitude), mapInfo.latPosition(latitude), -0.5f, -1f);
        
    }

    public View addLocationMarker(TileView rootView, Location location) {
        ImageView imageView = new ImageView(App.getAppContext());
        Drawable drawable = App.getAppContext().getDrawable(R.drawable.circlesmall);
        imageView.setImageDrawable(drawable);
        return rootView.addMarker(imageView, mapInfo.lngPosition(location.getLongitude()), mapInfo.latPosition(location
                        .getLatitude())
                , -0.5f, -0.5f);
    }
}
