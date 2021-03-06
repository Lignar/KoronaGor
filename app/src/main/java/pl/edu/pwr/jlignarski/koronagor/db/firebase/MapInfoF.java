package pl.edu.pwr.jlignarski.koronagor.db.firebase;

import io.realm.RealmObject;
import pl.edu.pwr.jlignarski.koronagor.db.realm.MapInfoR;

/**
 * @author janusz on 10.12.17.
 */

public class MapInfoF {
    private String mapRegex;
    private int width;
    private int height;
    private double xs;
    private double xe;
    private double ys;
    private double ye;
    private int iTiles;
    private int jTiles;
    private int tileSize;

    public MapInfoF() {
    }

    public MapInfoF(String mapRegex, int width, int height, double xs, double xe, double ys, double ye, int tileSize) {
        this.mapRegex = mapRegex;
        this.width = width;
        this.height = height;
        this.xs = xs;
        this.xe = xe;
        this.ys = ys;
        this.ye = ye;
        this.tileSize = tileSize;
    }

    public MapInfoF(MapInfoR mapInfo) {
        this(mapInfo.getMapRegex(), mapInfo.getWidth(), mapInfo.getHeight(), mapInfo.getXs(), mapInfo.getXe(), mapInfo.getYs(),
                mapInfo.getYe(), mapInfo.getTileSize());
    }

    public String getMapRegex() {
        return mapRegex;
    }

    public void setMapRegex(String mapRegex) {
        this.mapRegex = mapRegex;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getXs() {
        return xs;
    }

    public void setXs(double xs) {
        this.xs = xs;
    }

    public double getXe() {
        return xe;
    }

    public void setXe(double xe) {
        this.xe = xe;
    }

    public double getYs() {
        return ys;
    }

    public void setYs(double ys) {
        this.ys = ys;
    }

    public double getYe() {
        return ye;
    }

    public void setYe(double ye) {
        this.ye = ye;
    }

    public int getiTiles() {
        return iTiles;
    }

    public void setiTiles(int iTiles) {
        this.iTiles = iTiles;
    }

    public int getjTiles() {
        return jTiles;
    }

    public void setjTiles(int jTiles) {
        this.jTiles = jTiles;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
