package pl.edu.pwr.jlignarski.koronagor.model;

import android.graphics.Path;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author janusz on 09.01.18.
 */

class Trip {

    List<LatLng> positions = new ArrayList<>();

    public void addPoint(Location lastLocation) {
        positions.add(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
    }

    public boolean hasPoints() {
        return positions.size() > 1;
    }

    public Path buildPath(MapInfo mapInfo) {
        Path path = new Path();
        LatLng latLng = positions.get(0);
        path.moveTo((float)mapInfo.lngPosition(latLng.longitude), (float)mapInfo.latPosition(latLng.latitude));
        for (int i = 1; i < positions.size(); i++) {
            latLng = positions.get(i);
            path.lineTo((float)mapInfo.lngPosition(latLng.longitude), (float)mapInfo.latPosition(latLng.latitude));
        }
        return path;
    }
}
