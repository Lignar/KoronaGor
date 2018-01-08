package pl.edu.pwr.jlignarski.koronagor.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.edu.pwr.jlignarski.koronagor.R;
import pl.edu.pwr.jlignarski.koronagor.model.BitmapProviderInternalStorage;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 11.12.17.
 */

public class PeakDetailsViewMvp implements MvpView {

    private final View rootView;
    private final Context context;
    private Button touristButton;
    private Button googleButton;
    private Button pictureButton;
    private PeakDetailViewListener listener;
    private RecyclerView startingPointList;
    private Button additionalButton;
    private TextView peakName;

    public PeakDetailsViewMvp(Context context, LayoutInflater inflater, ViewGroup container, Peak peak) {
        this.context = context;
        if(!peak.isCompleted()) {
            rootView = inflater.inflate(R.layout.fragment_peak_details, container, false);
            init(peak);
            prepareNotConqueredView();
        } else {
            rootView = inflater.inflate(R.layout.fragment_peak_details_complete, container, false);
            init(peak);
            prepareConqueredView(peak);
        }
    }

    private void prepareConqueredView(Peak peak) {
        TextView conqueredDateText = rootView.findViewById(R.id.peakDetailsDate);
        TextView validationText = rootView.findViewById(R.id.peakDetailsValidation);
        TextView routeText = rootView.findViewById(R.id.peakDetailsRoute);
        TextView photoText = rootView.findViewById(R.id.peakDetailsPhoto);
        conqueredDateText.setText(String.format("Data: %s", peak.getConqueredDate()));
        peak.setValidationText(validationText);
        peak.setRouteText(routeText);
        peak.setPhotoText(photoText);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.takePicture();
            }
        });
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showPicture();
            }
        });
    }

    private void prepareNotConqueredView() {
        startingPointList = rootView.findViewById(R.id.startingPointList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        startingPointList.setLayoutManager(layoutManager);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.interactWithPicture();
            }
        });
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.markConquered();
            }
        });
    }

    private void init(Peak peak) {
        peakName = rootView.findViewById(R.id.peakName);
        TextView peakHeight = rootView.findViewById(R.id.peakDetailsHeight);
        TextView range = rootView.findViewById(R.id.peakDetailsRange);
        ImageView peakThumbnail = rootView.findViewById(R.id.peakThumbnail);
        googleButton = rootView.findViewById(R.id.googleMap);
        touristButton = rootView.findViewById(R.id.touristMap);
        pictureButton = rootView.findViewById(R.id.peakPicture);
        additionalButton = rootView.findViewById(R.id.peakDetailsAdditionalButton);
        peakThumbnail.setImageBitmap(peak.getThumbnail());
        peakName.setText(peak.getName());
        peakHeight.setText(peak.getHeight() + "m n.p.m.");
        range.setText(peak.getRange());
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
        peakName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.toggleTripRecording();
                return true;
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

    public void setAdapter(StartingPointListAdapter listAdapter) {
        startingPointList.setAdapter(listAdapter);
    }

    public interface PeakDetailViewListener {
        void openGoogleMap();

        void openTouristMap();

        void takePicture();

        void markConquered();

        void showPicture();

        void interactWithPicture();

        void toggleTripRecording();
    }
}
