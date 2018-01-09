package pl.edu.pwr.jlignarski.koronagor.db.hardcoded;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import pl.edu.pwr.jlignarski.koronagor.db.SystemRepository;
import pl.edu.pwr.jlignarski.koronagor.model.App;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;
import pl.edu.pwr.jlignarski.koronagor.model.StartingPoint;

/**
 * @author janusz on 08.12.17.
 */

public class StaticSystemRepository implements SystemRepository {

    private static StaticSystemRepository instance;
    private List<Peak> peaks;

    private StaticSystemRepository() {
    }

    public static SystemRepository getInstance() {
        if (instance == null) {
            instance = new StaticSystemRepository();
        }
        return instance;
    }

    @Override
    public List<Peak> getAllPeaks() {
        if (peaks == null) {
            StartingPoint sp11 = new StartingPoint("Palenica Białczańska", 49.255833, 20.103056, 250);
            StartingPoint sp12 = new StartingPoint("Morskie Oko", 49.201389, 20.071306, 150);
            Peak peak1 = new Peak(1, "Rysy", 2499, "Tatry", 49.179444, 20.088333,
                    Arrays.asList(sp11, sp12), "rysy_tile_%d_%d.png",
                    512, 512, 19.981190, 20.113546, 49.242975, 49.156546, true);
            StartingPoint sp21 = new StartingPoint("Markowe Szczawino", 49.587778, 19.516667, 90);
            StartingPoint sp22 = new StartingPoint("Kiczory", 49.545278, 19.545278, 220);
            Peak peak2 = new Peak(2, "Babia Góra", 1725, "Beskid Żywiecki", 49.573333, 19.529444,
                    Arrays.asList(sp21, sp22), "", 0, 0, 0, 0, 0, 0, true);
            Peak peak3 = new Peak(3, "Śnieżka", 1603, "Karkonosze");
            Peak peak4 = new Peak(4, "Śnieżnik", 1425, "Masyw Śnieżnika");
            Peak peak5 = new Peak(5, "Tarnica", 1346, "Bieszczady Zachodnie");
            Peak peak6 = new Peak(6, "Turbacz", 1310, "Gorce");
            Peak peak7 = new Peak(7, "Radziejowa", 1266, "Beskid Sądecki");
            Peak peak8 = new Peak(8, "Rynek", 0, "Wroclaw", 51.110111, 17.031964,
                    new ArrayList<StartingPoint>(), "wroclaw_tile_%d_%d.jpg",
                    581, 480, 17.019701, 17.050512, 51.118892, 51.102716, false);
            peaks = Arrays.asList(peak1, peak2, peak3, peak4, peak5, peak6, peak7, peak8);
        }
        return peaks;
    }

    @Override
    public Peak getPeakById(final int peakId) {
        Peak result = null;
        Iterator<Peak> iterator = getAllPeaks().iterator();
        while (iterator.hasNext() && result == null) {
            Peak current = iterator.next();
            if (current.getId() == peakId) {
                result = current;
            }
        }
        return result;
    }

    @Override
    public void loadMapsToStorage() {
        BitmapFactory.Options OPTIONS = new BitmapFactory.Options();
        OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
        AssetManager assetManager = App.getAppContext().getAssets();
        for (Peak peak : getAllPeaks()) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    String unformattedFileName = peak.getMapRegex();
                    String formattedFileName = String.format( Locale.US, unformattedFileName, i, j );
                    try {
                        InputStream inputStream = assetManager.open( formattedFileName );
                        if( inputStream != null ) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, OPTIONS);
                                FileOutputStream fos = App.getAppContext().openFileOutput(formattedFileName, Context.MODE_PRIVATE);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
                                fos.flush();
                                fos.close();
                                inputStream.close();

                            } catch( OutOfMemoryError | Exception e ) {
                                // this is probably an out of memory error - you can try sleeping (this method won't be called in the UI thread) or try again (or give up)
                            }
                        }
                    } catch( Exception e ) {
                        // this is probably an IOException, meaning the file can't be found
                    }
                }
            }

        }
    }
}
