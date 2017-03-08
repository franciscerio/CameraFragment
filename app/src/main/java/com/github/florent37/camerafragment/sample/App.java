package com.github.florent37.camerafragment.sample;

import android.app.Application;

/**
 * Created by android on 3/7/17.
 */

public class App extends Application {

    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }


    public static App getInstance() {
        return INSTANCE;
    }


}
