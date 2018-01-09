package pl.edu.pwr.jlignarski.koronagor.db;

import pl.edu.pwr.jlignarski.koronagor.model.Conquest;

/**
 * @author janusz on 07.01.18.
 */

public interface UserRepository {
    Conquest getConquestByPeakId(int id);
}
