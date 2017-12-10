package pl.edu.pwr.jlignarski.koronagor;

import android.util.Log;

import com.qozix.tileview.TileView;

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

    public MapInfo(String mapRegex, int width, int height, double xs, double xe, double ys, double ye) {
        this.mapRegex = mapRegex;
        this.width = width;
        this.height = height;
        this.xs = xs;
        this.xe = xe;
        this.ys = ys;
        this.ye = ye;
    }

    public String getRegex() {
        return mapRegex;
    }

    public void setMapSize(TileView rootView) {
        rootView.setSize(width, height);
    }

    public double latPosition(double latitude) {
        Log.i(TAG, latitude + " " + ys + " " + ye);
        double v = ((ys - latitude) / (ys - ye)) * height;
        Log.i(TAG, Double.toString(v));
        return v;
    }

    public double lngPosition(double longitude) {
        Log.i(TAG, longitude + " " + xs + " " + xe);
        double v = ((longitude - xs) / (xe - xs)) * width;
        Log.i(TAG, Double.toString(v));
        return v;
    }
}
