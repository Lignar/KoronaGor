package pl.edu.pwr.jlignarski.koronagor.db;

import java.util.List;

import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 08.12.17.
 */

public interface SystemRepository {
    List<Peak> getAllPeaks();

    Peak getPeakById(int peakId);

    void loadMapsToStorage();
}
