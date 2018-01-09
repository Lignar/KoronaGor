package pl.edu.pwr.jlignarski.koronagor.db.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import pl.edu.pwr.jlignarski.koronagor.db.SystemRepository;
import pl.edu.pwr.jlignarski.koronagor.db.hardcoded.StaticSystemRepository;
import pl.edu.pwr.jlignarski.koronagor.model.*;
import pl.edu.pwr.jlignarski.koronagor.model.Peak;

/**
 * @author janusz on 09.01.18.
 */

public class RealmSystemRepository implements SystemRepository {

    private static RealmSystemRepository instance;

    private RealmSystemRepository() {
    }

    public static SystemRepository getInstance() {
        if (instance == null) {
            instance = new RealmSystemRepository();
        }
        return instance;
    }

    @Override
    public List<Peak> getAllPeaks() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PeakR> all = realm.where(PeakR.class).findAll();
        List<Peak> result = new ArrayList<>();
        for (PeakR peakR : all) {
            result.add(new Peak(peakR));
        }
        realm.close();
        return result;
    }

    @Override
    public Peak getPeakById(int peakId) {
        Realm realm = Realm.getDefaultInstance();
        PeakR peakR = realm.where(PeakR.class).equalTo("id", peakId).findFirst();
        Peak peak = new Peak(peakR);
        realm.close();
        return peak;
    }

    @Override
    public void loadMapsToStorage() {

    }

    @Override
    public void migrateFromStatic() {
        List<Peak> allPeaks = StaticSystemRepository.getInstance().getAllPeaks();
        List<PeakR> peakRS = new ArrayList<>();
        for (Peak peak : allPeaks) {
            peakRS.add(peak.toRealm());
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(peakRS);
        realm.commitTransaction();
        realm.close();
    }
}
