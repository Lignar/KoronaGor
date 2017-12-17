package pl.edu.pwr.jlignarski.koronagor.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 11.12.17.
 */

public class PeakDetailsViewMvp implements MvpView {

    private final View rootView;
    private Button touristButton;
    private Button googleButton;
    private Button pictureButton;
    private PeakDetailViewListener listener;

    public PeakDetailsViewMvp(LayoutInflater inflater, ViewGroup container, Peak peak) {
        if(peak.getName().equals("Rysy")) {
            rootView = inflater.inflate(R.layout.fragment_peak_details, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_peak_details_complete, container, false);
        }
        init();
    }

    private void init() {
        googleButton = rootView.findViewById(R.id.googleMap);
        touristButton = rootView.findViewById(R.id.touristMap);
        pictureButton = rootView.findViewById(R.id.peakPicture);
        initListeners();
    }

    private void initListeners() {
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openGoogleMap();
            }
        });
        touristButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openTouristMap();
            }
        });
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.takePicture();
            }
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void attachListener(PeakDetailViewListener listener) {
        this.listener = listener;
    }

    public interface PeakDetailViewListener {
        void openGoogleMap();

        void openTouristMap();

        void takePicture();
    }
}
