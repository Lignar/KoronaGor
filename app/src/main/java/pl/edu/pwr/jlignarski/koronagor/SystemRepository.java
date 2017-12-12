package pl.edu.pwr.jlignarski.koronagor;

import java.util.List;

/**
 * @author janusz on 08.12.17.
 */

interface SystemRepository {
    List<Peak> getAllPeaks();

    Peak getPeakById(String peakId);

    void loadMapsToStorage();
}
