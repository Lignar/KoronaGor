package pl.edu.pwr.jlignarski.koronagor.model;

/**
 * @author janusz on 07.01.18.
 */

interface UserRepository {
    Conquest getConquestByPeakId(int id);
}
