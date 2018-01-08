package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.pwr.jlignarski.koronagor.view.PeakDetailsViewMvp;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.view.StartingPointListAdapter;

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
        PeakDetailsViewMvp peakDetailsView = new PeakDetailsViewMvp(getContext(), inflater, container, peak);
        peakDetailsView.attachListener(this);
        if (!peak.isCompleted()) {
            StartingPointListAdapter listAdapter = new StartingPointListAdapter(peak.getStartingPoints());
            peakDetailsView.setAdapter(listAdapter);
        }
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

    @Override
    public void markConquered() {
        mListener.markConquered();
    }

    @Override
    public void showPicture() {
        mListener.showPicture();
    }

    @Override
    public void interactWithPicture() {
        if (peak.hasPhoto()) {
            showPictureDialog();
        } else {
            mListener.takePicture();
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Istnieje zdjęcie");
        builder.setMessage("Istnieje już zdjęcie przypisane do tego szczytu. Zrobienie nowego zdjęcia nadpisze poprzednie!")
                .setPositiveButton("Zrób zdjęcie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.takePicture();
                    }
                })
                .setNegativeButton("Pokaż zdjęcie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.showPicture();
                    }
                });
        builder.create().show();
    }

    public interface OnPeakDetailsInteractionListener {
        void openGoogleMap();

        void openTouristMap();

        void takePicture();

        void markConquered();

        void showPicture();
    }
}
