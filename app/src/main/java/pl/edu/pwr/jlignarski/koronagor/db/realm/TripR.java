package pl.edu.pwr.jlignarski.koronagor.db.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import pl.edu.pwr.jlignarski.koronagor.model.Trip;

/**
 * @author janusz on 09.01.18.
 */

public class TripR extends RealmObject {

    RealmList<LatLngWrapperR> positions;

    public RealmList<LatLngWrapperR> getPositions() {
        return positions;
    }

    public void setPositions(RealmList<LatLngWrapperR> positions) {
        this.positions = positions;
    }
}
