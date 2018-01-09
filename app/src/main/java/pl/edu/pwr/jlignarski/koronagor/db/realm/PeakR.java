package pl.edu.pwr.jlignarski.koronagor.db.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author janusz on 08.12.17.
 */

public class PeakR extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private int height;
    private String range;
    private double latitude;
    private double longitude;
    private RealmList<StartingPointR> startingPoints;
    private MapInfoR mapInfo;

    public PeakR() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
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

    public RealmList<StartingPointR> getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(RealmList<StartingPointR> startingPoints) {
        this.startingPoints = startingPoints;
    }

    public MapInfoR getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfoR mapInfo) {
        this.mapInfo = mapInfo;
    }
}
