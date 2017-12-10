package pl.edu.pwr.jlignarski.koronagor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author janusz on 08.12.17.
 */

class StaticSystemRepository implements SystemRepository {

    static StaticSystemRepository instance;

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
        StartingPoint sp11 = new StartingPoint("Palenica Białczańska", 49.255833, 20.103056, 250);
        StartingPoint sp12 = new StartingPoint("Morskie Oko", 49.201389, 20.071306, 150);
        Peak peak1 = new Peak("Rysy", 2499, "Tatry", 49.179444, 20.088333, Arrays.asList(sp11, sp12));
        StartingPoint sp21 = new StartingPoint("Markowe Szczawino", 49.587778, 19.516667, 90);
        StartingPoint sp22 = new StartingPoint("Kiczory", 49.545278, 19.545278, 220);
        Peak peak2 = new Peak("Babia Góra", 1725, "Beskid Żywiecki", 49.573333, 19.529444, Arrays.asList(sp21, sp22));
        return Arrays.asList(peak1, peak2);
    }

    @Override
    public Peak getPeakById(final String peakId) {
        Peak result = null;
        Iterator<Peak> iterator = getAllPeaks().iterator();
        while (iterator.hasNext() && result == null) {
            Peak current = iterator.next();
            if (current.getName().equals(peakId)) {
                result = current;
            }
        }
        return result;
    }
}
