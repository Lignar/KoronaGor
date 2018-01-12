package pl.edu.pwr.jlignarski.koronagor.model;

import android.util.Log;

import com.qozix.tileview.TileView;

import pl.edu.pwr.jlignarski.koronagor.db.realm.MapInfoR;

/**
 * @author janusz on 10.12.17.
 */

class MapInfo {
    private static final String TAG = "MapInfo";
    private final String mapRegex;
    private final int width;
    private final int height;
    private final double xs;
    private final double xe;
    private final double ys;
    private final double ye;
    private int tileSize;

    public MapInfo(String mapRegex, int width, int height, double xs, double xe, double ys, double ye) {
        this.mapRegex = mapRegex;
        this.width = width;
        this.height = height;
        this.xs = xs;
        this.xe = xe;
        this.ys = ys;
        this.ye = ye;
    }

    public MapInfo(MapInfoR mapInfo) {
        mapRegex = mapInfo.getMapRegex();
        width = mapInfo.getWidth();
        height = mapInfo.getHeight();
        xs = mapInfo.getXs();
        xe = mapInfo.getXe();
        ys = mapInfo.getYs();
        ye = mapInfo.getYe();
        tileSize = mapInfo.getTileSize();
    }

    public String getRegex() {
        return mapRegex;
    }

    public void setMapSize(TileView rootView) {
        rootView.setSize(width, height);
    }

    public double latPosition(double latitude) {
        double v = ((ys - latitude) / (ys - ye)) * height;
        return v;
    }

    public double lngPosition(double longitude) {
        double v = ((longitude - xs) / (xe - xs)) * width;
        return v;
    }

    public boolean isPositionOnMap(double latitude, double longitude) {
        return xs < longitude && longitude < xe && ye < latitude && latitude < ys;
    }

    public MapInfoR toRealm() {
        return new MapInfoR(mapRegex, width, height, xs, xe, ys, ye, tileSize);
    }

    public int getTileSize() {
        return tileSize;
    }
}
