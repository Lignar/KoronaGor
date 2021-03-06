package pl.edu.pwr.jlignarski.koronagor.model;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * @author janusz on 08.12.17.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LocationService.getInstance();
        Realm.init(context);
    }

    public static Context getAppContext() {
        return context;
    }
}
