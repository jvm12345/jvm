package com.mydomain.takehomeapp.database;

import android.util.Log;

import com.mydomain.takehomeapp.database.productdb.ProductDBModel;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.annotations.RealmModule;

/*
 * Created by jmonani on 7/1/17.
 */
public class RealmDatabase {

    private static RealmDatabase mInstance = null;
    private static RealmConfiguration mReamConfig;
    private Realm mRealm = null;
    private static String TAG = RealmDatabase.class.getSimpleName();

    public static RealmDatabase getInstance() {
        if(mInstance == null)
            mInstance = new RealmDatabase();
        return mInstance;
    }

    private RealmDatabase(){
        int SCHEMA_VERSION = 1;
        String DB_NAME = "productDB.realm";
        mReamConfig = new RealmConfiguration.Builder()
                                        .schemaVersion(SCHEMA_VERSION)
                                        .name(DB_NAME)
                                        .modules(Realm.getDefaultModule(), new ProductDBModule())
                                        .migration(ProductDBMigration)
                                        .build();
    }

    public Realm getRealm() {

        try {
            mRealm = Realm.getInstance(mReamConfig);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if(null != mRealm && !mRealm.isClosed())
                mRealm.close();
            Realm.deleteRealm(mReamConfig);
            mRealm = Realm.getInstance(mReamConfig);
        }
        return mRealm;
    }

    public void deleteProductDB(){
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    /*
     *  product db module
     */
    @RealmModule(classes = {ProductDBModel.class})
    private class ProductDBModule {

    }

    /*
     * product db migration
     */
    private RealmMigration ProductDBMigration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        }
    };
}
