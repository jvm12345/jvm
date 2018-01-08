package com.mydomain.takehomeapp;

import android.app.Application;

import com.mydomain.takehomeapp.services.apihelper.ProductDetails;

import java.util.List;

/*
 * Created by jmonani on 1/7/2018.
 */

public class HomeApplication extends Application {

    private static HomeApplication mInstance;
    private List<ProductDetails> mProductDetailsList;

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
        //TODO
    }

    // TODO add persistence solution
    public void setProductList(List<ProductDetails> list) {
        this.mProductDetailsList = list;
    }

    public List<ProductDetails> getProductList() {
        return mProductDetailsList;
    }

}
