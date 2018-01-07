package pl.edu.pwr.jlignarski.koronagor.model;

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
