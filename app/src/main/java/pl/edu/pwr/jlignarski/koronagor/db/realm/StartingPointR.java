package pl.edu.pwr.jlignarski.koronagor.db.realm;

import io.realm.RealmObject;
import pl.edu.pwr.jlignarski.koronagor.db.firebase.StartingPointF;

/**
 * @author janusz on 09.12.17.
 */

public class StartingPointR extends RealmObject {
    private String name;
    private double latitude;
    private double longitude;
    private int reqTime;

    public StartingPointR() {
    }

    public StartingPointR(String name, double latitude, double longitude, int reqTime) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reqTime = reqTime;
    }

    public StartingPointR(StartingPointF startingPointF) {
        this(startingPointF.getName(), startingPointF.getLatitude(), startingPointF.getLongitude(), startingPointF.getReqTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getReqTime() {
        return reqTime;
    }

    public void setReqTime(int reqTime) {
        this.reqTime = reqTime;
    }
}
