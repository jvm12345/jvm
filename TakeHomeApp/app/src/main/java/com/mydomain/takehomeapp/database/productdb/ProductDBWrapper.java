package com.mydomain.takehomeapp.database.productdb;


/*
 * Created by jmonani on 1/7/2018.
 * product details db wrapper
 */

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductDBWrapper {

    private static final String TAG = ProductDBWrapper.class.getSimpleName();

    public ProductDBWrapper() {
    }

    /*
     * insert product details
     */
    public boolean insertProductDetail(Realm realm, ProductDBModel productDBModel) {
        boolean isSuccess = false;
        try {
            realm.beginTransaction();
            realm.insert(productDBModel);
            realm.commitTransaction();
            isSuccess = true;
        } catch (Exception e) {
            realm.cancelTransaction();
        }
        return isSuccess;
    }

    /*
     *  get product details
     */
    public ProductDBModel getProductDetailRecord(Realm realm, String id) {

        ProductDBModel model = realm.where(ProductDBModel.class).equalTo("productId", id).findFirst();
        return model;
    }

    /*
     * retrieve all product models
     */
    public RealmResults<ProductDBModel> retrieveAllProductRecordsAsync(Realm realm) {
        RealmResults<ProductDBModel> productDBRealmResults = realm.where(ProductDBModel.class).findAllAsync();
        return  productDBRealmResults;
    }

    /*
     * delete all product detail records
     */
    public void deleteAllProductRecords(Realm realm) {
        try {
            realm.beginTransaction();
            RealmResults<ProductDBModel> results = realm.where(ProductDBModel.class).findAll();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /*
    * count of records
    */
    public long countProductRecords(Realm realm) {
        long count = realm.where(ProductDBModel.class).count();
        return  count;
    }

}
