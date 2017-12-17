package pl.edu.pwr.jlignarski.koronagor.presenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author janusz on 09.12.17.
 */

enum BundleKey {

    PEAK_ID("peak_id");

    private String key;

    BundleKey(String key) {
        this.key = key;
    }

    public Object fromBundle(Bundle extras) {
        return extras.get(key);
    }

    public void addToIntent(Intent intent, String id) {
        intent.putExtra(key, id);
    }
}
