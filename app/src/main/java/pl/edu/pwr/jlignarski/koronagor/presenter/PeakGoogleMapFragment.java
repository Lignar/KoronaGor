package pl.edu.pwr.jlignarski.koronagor.presenter;

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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import pl.edu.pwr.jlignarski.koronagor.model.LocationService;
import pl.edu.pwr.jlignarski.koronagor.view.PeakGoogleMapViewMvp;
import pl.edu.pwr.jlignarski.koronagor.model.MarkerOptionsWrapper;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.model.RouteDrawingService;


public class PeakGoogleMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private PeakGoogleMapViewMvp peakGoogleMapView;
    private OnPeakMapInteractionListener mListener;
    private GoogleMap map;
    private RouteDrawingService routeDrawingService;
    private Location lastLocation;
    private Marker activeMarker;
    private Peak peak;

    public static PeakGoogleMapFragment newInstance(Peak peak) {
        PeakGoogleMapFragment peakGoogleMapFragment = new PeakGoogleMapFragment();
        peakGoogleMapFragment.setPeak(peak);
        return peakGoogleMapFragment;
    }

    public PeakGoogleMapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        peakGoogleMapView = new PeakGoogleMapViewMvp(inflater, container);
        peakGoogleMapView.getMap().onCreate(savedInstanceState);
        peakGoogleMapView.getMap().getMapAsync(this);
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
        LocationService.getInstance().registerLocationObserver(this);
        drawPeakMarkers();
    }

    private void drawPeakMarkers() {
        for (MarkerOptionsWrapper marker : peak.getMapMarkers()) {
            marker.addToMap(map);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(peak.getLatLng(), 11));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                activeMarker = null;
                if (marker.getTag() != null) {
                    redrawRoute(marker);
                    activeMarker = marker;
                }
                return false;
            }
        });
    }

    private void redrawRoute(Marker marker) {
        getRouteDrawingService().drawRoute(getLastLatLng(), (LatLng) marker.getTag());
    }

    private LatLng getLastLatLng() {
        LatLng result = null;
        if (lastLocation != null) {
            result = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        }
        return result;
    }

    private RouteDrawingService getRouteDrawingService() {
        if (routeDrawingService == null) {
            routeDrawingService = new RouteDrawingService(map);
        }
        return routeDrawingService;
    }

    @Override
    public void onResume() {
        peakGoogleMapView.getMap().onResume();
        super.onResume();
        LocationService.getInstance().registerLocationObserver(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        peakGoogleMapView.getMap().onPause();
        LocationService.getInstance().unregisterLocationObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        peakGoogleMapView.getMap().onDestroy();
        activeMarker = null;
        routeDrawingService = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        peakGoogleMapView.getMap().onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (activeMarker != null) {
            redrawRoute(activeMarker);
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        }
    }

    public void setPeak(Peak peak) {
        this.peak = peak;
    }

    public interface OnPeakMapInteractionListener {

    }
}
