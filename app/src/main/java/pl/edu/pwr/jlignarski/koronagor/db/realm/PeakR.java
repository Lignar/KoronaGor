package pl.edu.pwr.jlignarski.koronagor.db.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pl.edu.pwr.jlignarski.koronagor.db.firebase.PeakF;
import pl.edu.pwr.jlignarski.koronagor.db.firebase.StartingPointF;

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

    public PeakR(PeakF value) {
        this(value.getId(), value.getName(), value.getHeight(), value.getRange(), value.getLatitude(), value.getLongitude());
        startingPoints = new RealmList<>();
        if (value.getStartingPoints() != null) {
            for (StartingPointF startingPointF : value.getStartingPoints()) {
                startingPoints.add(new StartingPointR(startingPointF));
            }
        }
        if (value.getMapInfo() != null) {
            mapInfo = new MapInfoR(value.getMapInfo());
        }
    }

    public PeakR(int id, String name, int height, String range, double latitude, double longitude) {
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
