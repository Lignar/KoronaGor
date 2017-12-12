package pl.edu.pwr.jlignarski.koronagor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PeakDetailsFragment extends Fragment implements PeakDetailsViewMvp.PeakDetailViewListener {

    private OnPeakDetailsInteractionListener mListener;
    private Peak peak;

    public static PeakDetailsFragment newInstance(Peak peak) {
        PeakDetailsFragment peakTouristMapFragment = new PeakDetailsFragment();
        peakTouristMapFragment.setPeak(peak);
        return peakTouristMapFragment;
    }

    public PeakDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PeakDetailsViewMvp peakDetailsView = new PeakDetailsViewMvp(inflater, container, peak);
        peakDetailsView.attachListener(this);
        return peakDetailsView.getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPeakDetailsInteractionListener) {
            mListener = (OnPeakDetailsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPeakDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setPeak(Peak peak) {
        this.peak = peak;
    }

    @Override
    public void openGoogleMap() {
        mListener.openGoogleMap();
    }

    @Override
    public void openTouristMap() {
        mListener.openTouristMap();
    }

    @Override
    public void takePicture() {
        mListener.takePicture();
    }

    public interface OnPeakDetailsInteractionListener {
        void openGoogleMap();

        void openTouristMap();

        void takePicture();
    }
}
