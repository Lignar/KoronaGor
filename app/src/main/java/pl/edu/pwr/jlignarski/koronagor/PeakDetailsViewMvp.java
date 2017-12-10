package pl.edu.pwr.jlignarski.koronagor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author janusz on 11.12.17.
 */

class PeakDetailsViewMvp implements MvpView {

    private final View rootView;
    private Button touristButton;
    private Button googleButton;
    private PeakDetailViewListener listener;

    public PeakDetailsViewMvp(LayoutInflater inflater, ViewGroup container, Peak peak) {
        rootView = inflater.inflate(R.layout.fragment_peak_details, container, false);
        init();
    }

    private void init() {
        googleButton = rootView.findViewById(R.id.googleMap);
        touristButton = rootView.findViewById(R.id.touristMap);
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
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void attachListener(PeakDetailViewListener listener) {
        this.listener = listener;
    }

    interface PeakDetailViewListener {
        void openGoogleMap();
        void openTouristMap();
    }
}
