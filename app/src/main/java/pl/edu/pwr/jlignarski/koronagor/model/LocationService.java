package pl.edu.pwr.jlignarski.koronagor.model;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.HashSet;
import java.util.Set;

/**
 * @author janusz on 08.01.18.
 */

public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationService";
    private static final int UPDATE_INTERVAL = 10000;
    private static LocationService instance;
    private Set<LocationListener> locationObservers = new HashSet<>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private boolean isRecording;
    private Peak peak;
    private Trip trip;

    private LocationService() {
    }

    public static synchronized LocationService getInstance() {
        if (instance == null) {
            instance = new LocationService();
            instance.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(App.getAppContext());
            instance.buildGoogleApiClient();
        }
        return instance;
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(App.getAppContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private Location lastLocation;
    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i(TAG, "Location: " + location.getLatitude() + " " + location.getLongitude());
                lastLocation = location;
            }
            for (LocationListener observer : locationObservers) {
                observer.onLocationChanged(lastLocation);
            }
            if (isRecording) {
                peak.addTripPoint(trip, lastLocation);
            }
        }

    };

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        Log.i(TAG, "requestLocationUpdates");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, connectionResult.toString());
    }

    public void clearObservers() {
        locationObservers.clear();
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void registerLocationObserver(LocationListener listener) {
        if (lastLocation != null) {
            listener.onLocationChanged(lastLocation);
        }
        locationObservers.add(listener);
    }

    public void unregisterLocationObserver(LocationListener listener) {
        locationObservers.remove(listener);
    }

    public void toggleTripRecording(Peak peak) {
        if (canRecord(peak)) {
            if (!isRecording) {
                isRecording = true;
                this.peak = peak;
                trip = peak.createTrip();
                peak.addTripPoint(trip, lastLocation);
                Toast.makeText(App.getAppContext(), "Rozpoczęto nagrywanie", Toast.LENGTH_LONG).show();
            } else {
                isRecording = false;
                Toast.makeText(App.getAppContext(), "Zakończono nagrywanie", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(App.getAppContext(), "Nie znajdujesz się w pobliżu szczytu!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean canRecord(Peak peak) {
        return lastLocation != null && peak.isPositionOnMap(lastLocation);
    }

    public Trip getCurrentTrip() {
        return isRecording ? trip : null;
    }
}
