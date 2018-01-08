package pl.edu.pwr.jlignarski.koronagor.model;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author janusz on 07.01.18.
 */

class Conquest {
    private boolean completed;
    private String conqueredDate;
    private boolean validated;
    private Object route;
    private LatLng photoPosition;

    public boolean isCompleted() {
        return completed;
    }

    public String getConqueredDate() {
        return conqueredDate;
    }

    public void setValidationText(TextView validationText) {
        if(validated) {
           validationText.setText("Potwierdzono pozycją GPS");
           validationText.setTextColor(Color.rgb(65, 206, 117));
        } else {
            validationText.setText("Nie potwierdzono pozycją GPS");
            validationText.setTextColor(Color.GRAY);
        }
    }

    public void setRouteText(TextView routeText) {
        if (route != null) {
            routeText.setText("Zapisano trasę!");
        } else {
            routeText.setVisibility(View.GONE);
        }
    }

    public void setPhotoText(TextView photoText) {
        if (hasPhoto()) {
            photoText.setText("Dołączono zdjęcie!");
        } else {
            photoText.setVisibility(View.GONE);
        }
    }

    private boolean hasPhoto() {
        return photoPosition != null;
    }
}
