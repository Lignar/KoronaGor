package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.model.RepositoryDelegate;

public class PeakMapActivity extends AppCompatActivity implements PeakGoogleMapFragment.OnPeakMapInteractionListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        PeakTouristMapFragment.OnTouristMapInteractionListener, PeakDetailsFragment.OnPeakDetailsInteractionListener {

    private static final String TAG = "PeakMapActivity";
    private static final int UPDATE_INTERVAL = 60000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
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
        return RepositoryDelegate.getSystemRepo().getPeakById((int) BundleKey.PEAK_ID.fromBundle(getIntent().getExtras()));
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

    @Override
    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "pl.edu.pwr.jlignarski.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File tempFile = File.createTempFile(timeStamp, ".jpg", storageDir);
        Log.i(TAG, tempFile.getAbsolutePath());
        return tempFile;
    }
}
