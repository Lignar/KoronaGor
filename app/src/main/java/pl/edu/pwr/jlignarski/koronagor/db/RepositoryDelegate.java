package pl.edu.pwr.jlignarski.koronagor.db;

import pl.edu.pwr.jlignarski.koronagor.db.hardcoded.StaticSystemRepository;
import pl.edu.pwr.jlignarski.koronagor.db.hardcoded.StaticUserRepository;
import pl.edu.pwr.jlignarski.koronagor.db.realm.RealmSystemRepository;
import pl.edu.pwr.jlignarski.koronagor.db.realm.RealmUserRepository;

/**
 * @author janusz on 08.12.17.
 */

public abstract class RepositoryDelegate {

    public static SystemRepository getSystemRepo() {
        return RealmSystemRepository.getInstance();
    }

    public static UserRepository getUserRepo() {
        return RealmUserRepository.getInstance();
    }
}
