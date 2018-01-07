package pl.edu.pwr.jlignarski.koronagor.model;

/**
 * @author janusz on 07.01.18.
 */

class StaticUserRepository implements UserRepository {

    private static StaticUserRepository instance;

    private StaticUserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new StaticUserRepository();
        }
        return instance;
    }

    @Override
    public Conquest getConquestByPeakId(int id) {
        if (id == 1) {
            return new Conquest() {
                @Override
                public boolean isCompleted() {
                    return true;
                }
            };
        } else {
            return new Conquest();
        }
    }
}
