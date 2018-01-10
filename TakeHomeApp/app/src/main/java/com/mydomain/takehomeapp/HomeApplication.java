package com.mydomain.takehomeapp;

import android.app.Application;

import io.realm.Realm;

/*
 * Created by jmonani on 1/7/2018.
 */

public class HomeApplication extends Application {

    private static HomeApplication mInstance;
    public static HomeApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mInstance.initializeInstance();
    }

    /*
     * initialize application level data
     */
    private void initializeInstance() {
        Realm.init(this);
    }
}
