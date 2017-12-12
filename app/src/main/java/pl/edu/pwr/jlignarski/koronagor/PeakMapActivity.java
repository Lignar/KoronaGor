package pl.edu.pwr.jlignarski.koronagor;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

public class PeakMapActivity extends AppCompatActivity implements PeakGoogleMapFragment.OnPeakMapInteractionListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        PeakTouristMapFragment.OnTouristMapInteractionListener, PeakDetailsFragment.OnPeakDetailsInteractionListener {

    private static final String TAG = "PeakMapActivity";
    private static final int UPDATE_INTERVAL = 10000;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Set<LocationListener> locationObservers = new HashSet<>();
    private Peak peak;
    private PeakGoogleMapFragment peakGoogleMapFragment;
    private PeakTouristMapFragment peakTouristMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak);
        peak = getPeakFromBundle();
        createPeakDetailsFragment();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buildGoogleApiClient();
    }

    private void createPeakDetailsFragment() {
        PeakDetailsFragment peakDetailsFragment = PeakDetailsFragment.newInstance(peak);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.peakContainer, peakDetailsFragment);
        fragmentTransaction.commit();
    }

    private Peak getPeakFromBundle() {
        return RepositoryDelegate.getSystemRepo().getPeakById((String) BundleKey.PEAK_ID.fromBundle(getIntent().getExtras()));
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i(TAG, "Location: " + location.getLatitude() + " " + location.getLongitude());
                lastLocation = location;
            }
            for (LocationListener observer : locationObservers) {
                observer.onLocationChanged(lastLocation);
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Log.i(TAG, "removeLocationUpdates");
        }
    }

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

    @Override
    public void registerLocationObserver(LocationListener listener) {
        if (lastLocation != null) {
            listener.onLocationChanged(lastLocation);
        }
        locationObservers.add(listener);
    }

    @Override
    public void unregisterLocationObserver(LocationListener listener) {
        locationObservers.remove(listener);
    }

    @Override
    public void openGoogleMap() {
        if (peakGoogleMapFragment == null) {
            peakGoogleMapFragment = PeakGoogleMapFragment.newInstance(peak);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.peakContainer, peakGoogleMapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void openTouristMap() {
        if (peakTouristMapFragment == null) {
            peakTouristMapFragment = PeakTouristMapFragment.newInstance(peak);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.peakContainer, peakTouristMapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
