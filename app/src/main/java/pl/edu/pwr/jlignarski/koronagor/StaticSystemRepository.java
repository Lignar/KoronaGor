package pl.edu.pwr.jlignarski.koronagor;

import java.util.Arrays;
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
        Peak peak1 = new Peak("Rysy", 2499, "Tatry");
        Peak peak2 = new Peak("Babia Góra", 1725, "Beskid Żywiecki");
        return Arrays.asList(peak1, peak2);
    }
}
