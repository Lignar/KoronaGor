package pl.edu.pwr.jlignarski.koronagor.db.realm;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pl.edu.pwr.jlignarski.koronagor.model.Trip;

/**
 * @author janusz on 07.01.18.
 */

public class ConquestR extends RealmObject {
    @PrimaryKey
    private int id;
    private boolean completed;
    private String conqueredDate;
    private boolean validated;
    private LatLngWrapperR photoPosition;
    private RealmList<TripR> trips;

    public ConquestR() {
    }

    public ConquestR(boolean completed, String conqueredDate, boolean validated, LatLng photoPosition, List<Trip> trips) {
        this.completed = completed;
        this.conqueredDate = conqueredDate;
        this.validated = validated;
        if (photoPosition != null) {
            this.photoPosition = new LatLngWrapperR(photoPosition);
        }
        this.trips = new RealmList<>();
        for (Trip trip : trips) {
            this.trips.add(trip.toRealm());
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getConqueredDate() {
        return conqueredDate;
    }

    public void setConqueredDate(String conqueredDate) {
        this.conqueredDate = conqueredDate;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public LatLngWrapperR getPhotoPosition() {
        return photoPosition;
    }

    public void setPhotoPosition(LatLngWrapperR photoPosition) {
        this.photoPosition = photoPosition;
    }

    public RealmList<TripR> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<TripR> trips) {
        this.trips = trips;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
