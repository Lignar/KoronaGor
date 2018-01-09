package pl.edu.pwr.jlignarski.koronagor.model;

import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author janusz on 07.01.18.
 */

public class Conquest {
    private boolean completed;
    private String conqueredDate;
    private boolean validated;
    private LatLng photoPosition;
    private List<Trip> trips = new ArrayList<>();

    public boolean isCompleted() {
        return completed;
    }

    public String getConqueredDate() {
        return conqueredDate;
    }

    public void setValidationText(TextView validationText) {
        if(validated) {
           validationText.setText("Potwierdzono pozycją GPS");
           validationText.setTextColor(Color.rgb(65, 206, 117));
        } else {
            validationText.setText("Nie potwierdzono pozycją GPS");
            validationText.setTextColor(Color.GRAY);
        }
    }

    public void setRouteText(TextView routeText) {
        if (!trips.isEmpty() && tripsHavePoints()) {
            routeText.setText("Zapisano trasę!");
        } else {
            routeText.setVisibility(View.GONE);
        }
    }

    private boolean tripsHavePoints() {
        boolean result = false;
        for (int i = 0; i < trips.size() && !result; i++) {
            result = trips.get(i).hasPoints();
        }
        return result;
    }

    public void setPhotoText(TextView photoText) {
        if (hasPhoto()) {
            photoText.setText("Dołączono zdjęcie!");
        } else {
            photoText.setVisibility(View.GONE);
        }
    }

    boolean hasPhoto() {
        return photoPosition != null;
    }

    public void conquer(boolean verified) {
        completed = true;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        conqueredDate = format.format(Calendar.getInstance().getTime());
        this.validated = verified;
    }

    public void photoAdded() {
        Location lastLocation = LocationService.getInstance().getLastLocation();
        if (lastLocation != null) {
            photoPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        } else {
            photoPosition = new LatLng(0, 0);
        }
    }

    public MarkerOptionsWrapper buildMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(photoPosition);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        return new MarkerOptionsWrapper(markerOptions, true);
    }

    public Trip createTrip() {
        Trip trip = new Trip();
        trips.add(trip);
        return trip;
    }

    public List<Path> buildPaths(MapInfo mapInfo) {
        List<Path> result = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.hasPoints() && (!trip.equals(LocationService.getInstance().getCurrentTrip()))) {
                result.add(trip.buildPath(mapInfo));
            }
        }
        return result;
    }
}
