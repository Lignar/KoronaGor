package pl.edu.pwr.jlignarski.koronagor.model;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qozix.tileview.TileView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.db.RepositoryDelegate;
import pl.edu.pwr.jlignarski.koronagor.db.UserRepository;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.db.realm.StartingPointR;

/**
 * @author janusz on 08.12.17.
 */

public class Peak {

    private int id;
    private String name;
    private int height;
    private String range;
    private final double latitude;
    private final double longitude;
    private List<StartingPoint> startingPoints = new ArrayList<>();
    private MapInfo mapInfo;
    private Conquest conquest;

    public Peak(int id, String name, int height, String range, double latitude, double longitude, List<StartingPoint>
            startingPoints,
                String mapRegex, int width, int mapHeight, double xs, double xe, double ys, double ye, boolean completed) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.range = range;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startingPoints = startingPoints;
        this.mapInfo = new MapInfo(mapRegex, width, mapHeight, xs, xe, ys, ye);
        conquest = RepositoryDelegate.getUserRepo().getConquestByPeakId(getId());
    }

    public Peak(int id, String name, int height, String range) {
        this(id, name, height, range, 0, 0, new ArrayList<StartingPoint>(), "", 0, 0, 0, 0, 0, 0, false);
    }

    public Peak(PeakR peakR) {
        if (peakR != null) {
            id = peakR.getId();
            name = peakR.getName();
            height = peakR.getHeight();
            range = peakR.getRange();
            latitude = peakR.getLatitude();
            longitude = peakR.getLongitude();
            for (StartingPointR startingPointR : peakR.getStartingPoints()) {
                startingPoints.add(new StartingPoint(startingPointR));
            }
            if (peakR.getMapInfo() != null) {
                mapInfo = new MapInfo(peakR.getMapInfo());
            }
            conquest = RepositoryDelegate.getUserRepo().getConquestByPeakId(getId());
        } else {
            latitude = 0;
            longitude = 0;
        }
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

    public int getId() {
        return id;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public List<MarkerOptionsWrapper> getMapMarkers() {
        List<MarkerOptionsWrapper> result = new ArrayList<>();
        result.add(buildMarker());
        for (StartingPoint startingPoint : startingPoints) {
            result.add(startingPoint.buildMarker());
        }
        if (conquest.hasPhoto()) {
            result.add(conquest.buildMarker());
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
        rootView.addMarker(createMarkerImageView(R.drawable.markergreen), mapInfo.lngPosition(longitude), mapInfo.latPosition
                (latitude), -0.5f, -1f);
        for (StartingPoint startingPoint : startingPoints) {
            LatLng latLng = startingPoint.getLatLng();
            rootView.addMarker(createMarkerImageView(R.drawable.marker), mapInfo.lngPosition(latLng.longitude), mapInfo.latPosition(latLng.latitude), -0.5f,
                    -1f);
        }
    }

    private ImageView createMarkerImageView(int marker) {
        ImageView imageView = new ImageView(App.getAppContext());
        Drawable drawable = App.getAppContext().getDrawable(marker);
        imageView.setImageDrawable(drawable);
        return imageView;
    }

    public View addLocationMarker(TileView rootView, Location location) {
        ImageView imageView = createMarkerImageView(R.drawable.circlesmall);
        return rootView.addMarker(imageView, mapInfo.lngPosition(location.getLongitude()), mapInfo.latPosition(location
                        .getLatitude())
                , -0.5f, -0.5f);
    }

    public boolean isCompleted() {
        return conquest.isCompleted();
    }

    public List<StartingPoint> getStartingPoints() {
        return startingPoints;
    }

    public String getConqueredDate() {
        return conquest.getConqueredDate();
    }

    public void setValidationText(TextView validationText) {
        conquest.setValidationText(validationText);
    }

    public void setRouteText(TextView routeText) {
        conquest.setRouteText(routeText);
    }

    public void setPhotoText(TextView photoText) {
        conquest.setPhotoText(photoText);
    }

    public void conquer(Location lastLocation) {
        conquest.conquer(verifyLocation(lastLocation));
        updateConquest();
    }

    private void updateConquest() {
        RepositoryDelegate.getUserRepo().updateConquest(id, conquest.toRealm());
    }

    private boolean verifyLocation(Location lastLocation) {
        Location peakLocation = new Location("peak");
        peakLocation.setLatitude(latitude);
        peakLocation.setLongitude(longitude);
        return (lastLocation != null &&
                lastLocation.distanceTo(peakLocation) < 500);
    }

    public boolean hasPhoto() {
        return conquest.hasPhoto();
    }

    public Bitmap getThumbnail() {
        return BitmapProviderInternalStorage.getBitmap(App.getAppContext(), String.format(getMapRegex(), -1, -1));
    }

    public Bitmap getPhoto() {
        return BitmapProviderInternalStorage.getBitmap(App.getAppContext(), String.format(getMapRegex(), -2, -2));
    }

    public void photoAdded() {
        conquest.photoAdded();
        updateConquest();
    }

    public boolean isPositionOnMap(Location lastLocation) {
        return mapInfo.isPositionOnMap(lastLocation.getLatitude(), lastLocation.getLongitude());
    }

    public Trip createTrip() {
        Trip trip = conquest.createTrip();
        updateConquest();
        return trip;
    }

    public void addTripPoint(Trip trip, Location lastLocation) {
        trip.addPoint(lastLocation);
        updateConquest();
    }

    public List<Path> buildPaths() {
        return conquest.buildPaths(mapInfo);
    }

    public PeakR toRealm() {
        PeakR peakR = new PeakR();
        peakR.setId(id);
        peakR.setHeight(height);
        peakR.setLatitude(latitude);
        peakR.setLongitude(longitude);
        peakR.setName(name);
        peakR.setRange(range);
        peakR.setMapInfo(mapInfo.toRealm());
        RealmList<StartingPointR> realmList = new RealmList<>();
        for (StartingPoint startingPoint : startingPoints) {
            realmList.add(startingPoint.toRealm());
        }
        peakR.setStartingPoints(realmList);
        return peakR;
    }
}
