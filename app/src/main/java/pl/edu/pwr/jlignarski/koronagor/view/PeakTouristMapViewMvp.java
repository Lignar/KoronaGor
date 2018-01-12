package pl.edu.pwr.jlignarski.koronagor.view;

import android.graphics.Paint;
import android.graphics.Path;
import android.location.Location;
import android.view.View;

import com.qozix.tileview.TileView;
import com.qozix.tileview.paths.CompositePathView;

import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.model.BitmapProviderInternalStorage;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

import static com.qozix.tileview.paths.CompositePathView.*;

/**
 * @author janusz on 10.12.17.
 */

public class PeakTouristMapViewMvp implements MvpView {

    private static final int DEFAULT_STROKE_COLOR = 0xFF088DA5;
    private static final int DEFAULT_STROKE_WIDTH = 8;

    private final TileView rootView;
    private Peak peak;

    public PeakTouristMapViewMvp(Peak peak) {
        this.peak = peak;
        this.rootView = new TileView(App.getAppContext());
        rootView.setBitmapProvider(new BitmapProviderInternalStorage());
        peak.setMapSize(rootView);
        peak.setDetailLevel(rootView);
        rootView.setScaleLimits(0f, 10f);
        peak.addMarkers(rootView);
        for (Path path : peak.buildPaths()) {
            DrawablePath drawablePath = new DrawablePath();
            drawablePath.path = path;
            drawablePath.paint = new Paint();
            drawablePath.paint.setStyle( Paint.Style.STROKE );
            drawablePath.paint.setColor( DEFAULT_STROKE_COLOR );
            drawablePath.paint.setStrokeWidth( DEFAULT_STROKE_WIDTH );
            drawablePath.paint.setAntiAlias( true );
            rootView.drawPath(drawablePath);
        }

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public View addLocationMarker(Location location) {
        return peak.addLocationMarker(rootView, location);
    }

    public void removeLocationMarker(View lastLocationMarker) {
        rootView.removeMarker(lastLocationMarker);
    }
}
