package pl.edu.pwr.jlignarski.koronagor;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PeakTouristMapFragment extends Fragment implements LocationListener {

    private OnTouristMapInteractionListener mListener;
    private Peak peak;
    private PeakTouristMapMvp peakTouristMapView;

    public PeakTouristMapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        peakTouristMapView = new PeakTouristMapMvp(peak);
        return peakTouristMapView.getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTouristMapInteractionListener) {
            mListener = (OnTouristMapInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTouristMapInteractionListener");
        }
        mListener.registerLocationObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mListener.unregisterLocationObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.registerLocationObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static PeakTouristMapFragment newInstance(Peak peak) {
        PeakTouristMapFragment peakTouristMapFragment = new PeakTouristMapFragment();
        peakTouristMapFragment.setPeak(peak);
        return peakTouristMapFragment;
    }

    public void setPeak(Peak peak) {
        this.peak = peak;
    }

    @Override
    public void onLocationChanged(Location location) {
        peakTouristMapView.addLocationMarker(location);
    }

    public interface OnTouristMapInteractionListener extends LocationObserverManager {
    }
}
