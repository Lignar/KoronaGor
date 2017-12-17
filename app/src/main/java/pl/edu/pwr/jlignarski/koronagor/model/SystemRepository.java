package pl.edu.pwr.jlignarski.koronagor.model;

import java.util.List;

/**
 * @author janusz on 08.12.17.
 */

public interface SystemRepository {
    List<Peak> getAllPeaks();

    Peak getPeakById(String peakId);

    void loadMapsToStorage();
}
