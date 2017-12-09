package pl.edu.pwr.jlignarski.koronagor;

/**
 * @author janusz on 09.12.17.
 */

enum BundleKey {

    PEAK_ID("peak_id");

    private String key;

    BundleKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
