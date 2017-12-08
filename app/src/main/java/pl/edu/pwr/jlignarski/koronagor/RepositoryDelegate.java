package pl.edu.pwr.jlignarski.koronagor;

/**
 * @author janusz on 08.12.17.
 */

abstract class RepositoryDelegate {

    public static SystemRepository getSystemRepo() {
        return StaticSystemRepository.getInstance();
    }

}
