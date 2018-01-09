package pl.edu.pwr.jlignarski.koronagor.db.firebase;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pl.edu.pwr.jlignarski.koronagor.db.realm.MapInfoR;
import pl.edu.pwr.jlignarski.koronagor.db.realm.PeakR;
import pl.edu.pwr.jlignarski.koronagor.db.realm.StartingPointR;

/**
 * @author janusz on 08.12.17.
 */

public class PeakF {

    private int id;
    private String name;
    private int height;
    private String range;
    private double latitude;
    private double longitude;
    private List<StartingPointF> startingPoints;
    private MapInfoF mapInfo;

    public PeakF() {

    }

    public PeakF(PeakR peakR) {
        this(peakR.getId(), peakR.getName(), peakR.getHeight(), peakR.getRange(), peakR.getLatitude(), peakR.getLongitude());
        startingPoints = new ArrayList<>();
        for (StartingPointR startingPointR : peakR.getStartingPoints()) {
            startingPoints.add(new StartingPointF(startingPointR));
        }
        if (peakR.getMapInfo() != null) {
            mapInfo = new MapInfoF(peakR.getMapInfo());
        }
    }

    public PeakF(int id, String name, int height, String range, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.range = range;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public List<StartingPointF> getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(List<StartingPointF> startingPoints) {
        this.startingPoints = startingPoints;
    }

    public MapInfoF getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfoF mapInfo) {
        this.mapInfo = mapInfo;
    }
}
