package pl.edu.pwr.jlignarski.koronagor.db.realm;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

/**
 * @author janusz on 09.01.18.
 */

public class LatLngWrapperR extends RealmObject {

    double latitute;
    double longitude;

    public LatLngWrapperR() {
    }

    public LatLngWrapperR(LatLng photoPosition) {
        latitute = photoPosition.latitude;
        longitude = photoPosition.longitude;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
