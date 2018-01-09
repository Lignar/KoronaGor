package pl.edu.pwr.jlignarski.koronagor.db;

import pl.edu.pwr.jlignarski.koronagor.db.hardcoded.StaticSystemRepository;
import pl.edu.pwr.jlignarski.koronagor.db.hardcoded.StaticUserRepository;

/**
 * @author janusz on 08.12.17.
 */

public abstract class RepositoryDelegate {

    public static SystemRepository getSystemRepo() {
        return StaticSystemRepository.getInstance();
    }

    public static UserRepository getUserRepo() {
        return StaticUserRepository.getInstance();
    }
}
