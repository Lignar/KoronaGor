package pl.edu.pwr.jlignarski.koronagor;

import android.app.Application;
import android.content.Context;

/**
 * @author janusz on 08.12.17.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
