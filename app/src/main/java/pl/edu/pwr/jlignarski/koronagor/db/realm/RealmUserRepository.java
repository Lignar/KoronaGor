package pl.edu.pwr.jlignarski.koronagor.db.realm;

import io.realm.Realm;
import pl.edu.pwr.jlignarski.koronagor.db.UserRepository;
import pl.edu.pwr.jlignarski.koronagor.model.*;
import pl.edu.pwr.jlignarski.koronagor.model.Conquest;

/**
 * @author janusz on 09.01.18.
 */

public class RealmUserRepository implements UserRepository {

    private static RealmUserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new RealmUserRepository();
        }
        return instance;
    }

    private RealmUserRepository() {
    }

    @Override
    public Conquest getConquestByPeakId(int id) {
        Realm realm = Realm.getDefaultInstance();
        ConquestR conquestR = realm.where(ConquestR.class).equalTo("id", id).findFirst();
        Conquest result = new Conquest(conquestR);
        realm.close();
        return result;
    }

    @Override
    public void updateConquest(int id, ConquestR conquestR) {
        conquestR.setId(id);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(conquestR);
        realm.commitTransaction();
        realm.close();
    }
}
