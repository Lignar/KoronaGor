package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.BitmapProviderInternalStorage;
import pl.edu.pwr.jlignarski.koronagor.model.LocationService;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.model.RepositoryDelegate;

public class PeakMapActivity extends AppCompatActivity implements PeakGoogleMapFragment.OnPeakMapInteractionListener,
        PeakTouristMapFragment.OnTouristMapInteractionListener, PeakDetailsFragment.OnPeakDetailsInteractionListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Peak peak;
    private PeakGoogleMapFragment peakGoogleMapFragment;
    private PeakTouristMapFragment peakTouristMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peak);
        getSupportActionBar().hide();
        peak = getPeakFromBundle();
        createPeakDetailsFragment();
    }

    private void createPeakDetailsFragment() {
        PeakDetailsFragment peakDetailsFragment = PeakDetailsFragment.newInstance(peak);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.peakContainer, peakDetailsFragment);
        fragmentTransaction.commit();
    }

    private Peak getPeakFromBundle() {
        return RepositoryDelegate.getSystemRepo().getPeakById((int) BundleKey.PEAK_ID.fromBundle(getIntent().getExtras()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationService.getInstance().clearObservers();
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
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void markConquered() {
        peak.conquer(LocationService.getInstance().getLastLocation());
        createPeakDetailsFragment();
    }

    @Override
    public void showPicture() {
        if (!peak.hasPhoto()) {
            Toast.makeText(this, "Nie przypisano zdjęcia!", Toast.LENGTH_LONG).show();
        }
        Fragment photoFragment = PhotoFragment.newInstance(peak.getPhoto());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.peakContainer, photoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void toggleTripRecording() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            BitmapProviderInternalStorage.saveBitmap(imageBitmap, String.format(peak.getMapRegex(), -1, -1));
        }
    }
}
