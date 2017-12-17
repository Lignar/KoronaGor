package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;

import pl.edu.pwr.jlignarski.koronagor.view.PeakTouristMapViewMvp;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

public class PeakTouristMapFragment extends Fragment implements LocationListener {

    private OnTouristMapInteractionListener mListener;
    private Peak peak;
    private PeakTouristMapViewMvp peakTouristMapView;
    private View lastLocationMarker;

    public PeakTouristMapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        peakTouristMapView = new PeakTouristMapViewMvp(peak);
        mListener.registerLocationObserver(this);
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
        if (lastLocationMarker != null) {
            peakTouristMapView.removeLocationMarker(lastLocationMarker);
        }
        lastLocationMarker = peakTouristMapView.addLocationMarker(location);
    }

    public interface OnTouristMapInteractionListener extends LocationObserverManager {
    }
}
