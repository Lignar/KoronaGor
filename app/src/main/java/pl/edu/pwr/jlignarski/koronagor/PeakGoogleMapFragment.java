package pl.edu.pwr.jlignarski.koronagor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;


public class PeakGoogleMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private PeakGoogleMapMvp peakGoogleMapView;
    private OnPeakMapInteractionListener mListener;
    private GoogleMap map;
    private MapView mapView;

    public static PeakGoogleMapFragment newInstance(Bundle bundle) {
        PeakGoogleMapFragment peakGoogleMapFragment = new PeakGoogleMapFragment();
        peakGoogleMapFragment.setArguments(bundle);
        return peakGoogleMapFragment;
    }

    public PeakGoogleMapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        peakGoogleMapView = new PeakGoogleMapMvp(inflater, container);
        mapView = peakGoogleMapView.getMap();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return peakGoogleMapView.getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPeakMapInteractionListener) {
            mListener = (OnPeakMapInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPeakMapInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        mListener.registerLocationObserver(this);
        drawPeakMarkers();
    }

    private void drawPeakMarkers() {
        Peak peak = RepositoryDelegate.getSystemRepo().getPeakById((String) getArguments().get(BundleKey.PEAK_ID.getKey()));
        for (MarkerOptions marker : peak.getMapMarkers()) {
            map.addMarker(marker);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(peak.getLatLng(), 11));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        mListener.registerLocationObserver(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        mListener.unregisterLocationObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    public interface OnPeakMapInteractionListener {
        void registerLocationObserver(LocationListener listener);

        void unregisterLocationObserver(LocationListener listener);
    }
}
