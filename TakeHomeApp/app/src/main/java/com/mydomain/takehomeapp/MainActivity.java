package com.mydomain.takehomeapp;
/*
 * Description: product list main activity
 * Created by jmonani on 12/21/17.
 *
 */

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mydomain.takehomeapp.ProductCatalogue.ProductListAdapter;
import com.mydomain.takehomeapp.database.RealmDatabase;
import com.mydomain.takehomeapp.database.productdb.ProductDBModel;
import com.mydomain.takehomeapp.database.productdb.ProductDBWrapper;
import com.mydomain.takehomeapp.services.ProductAPIService;
import com.mydomain.takehomeapp.services.apihelper.BaseApiResponse;
import com.mydomain.takehomeapp.services.apihelper.ProductDetailResponse;
import com.mydomain.takehomeapp.services.apihelper.ProductDetails;
import com.mydomain.takehomeapp.utility.AsyncCallbackInf;
import com.mydomain.takehomeapp.utility.ScrollListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private List<ProductDetails> mProductDetailsList;
    private static final int SCROLL_DOWN = 1;
    private static final int SUCCESS_RESPONSE = 200;
    private RecyclerView mProductListRecyclerView;
    private ProductListAdapter mProductListAdapter;
    private TextView mEmptyListMessage;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductDetailsList = new ArrayList<>();
        Realm realm = RealmDatabase.getInstance().getRealm();
        setTitle("Product List Screen");
        // get list fresh from server
        new ProductDBWrapper().deleteAllProductRecords(realm);
        realm.close();
        setContentView(R.layout.activity_main);
        mProductListRecyclerView = findViewById(R.id.product_recycler_view);
        mEmptyListMessage = findViewById(R.id.product_list_empty_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProductListRecyclerView.setLayoutManager(layoutManager);
        mProductListAdapter = new ProductListAdapter((ArrayList<ProductDetails>)mProductDetailsList, this);
        mProductListRecyclerView.setAdapter(mProductListAdapter);

        ScrollListener mScrollListener = new ScrollListener(layoutManager) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount, RecyclerView recyclerView) {
                if (mProductListRecyclerView.canScrollVertically(SCROLL_DOWN)) {
                    ProductDetails product = mProductDetailsList.get(mProductDetailsList.size() - 1);
                    fetchProductList(product.getPageNumber() + 1);
                }
                return true;
            }
        };
        mScrollListener.resetState();
        mProductListRecyclerView.setOnScrollListener(mScrollListener);
        if(mProductDetailsList.size() <= 0)
            fetchProductList(1);
    }

    public void fetchProductList(int page) {

        new ProductAPIService().getProductListAsync(page, new AsyncCallbackInf() {
            @Override
            public void onResponseCallback(int result, BaseApiResponse response) {
                ProductDetailResponse detailResponse = (ProductDetailResponse) response;
                if(null != detailResponse && null != detailResponse.status && SUCCESS_RESPONSE == detailResponse.status) {
                    setProductList(detailResponse);
                    mProductListAdapter.setData((ArrayList<ProductDetails>) mProductDetailsList);
                    mProductListAdapter.notifyDataSetChanged();
                    mEmptyListMessage.setVisibility(View.INVISIBLE);
                    mProductListRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // error scenario
                    String message = "Error fetching Product List:: ";
                    if(null != detailResponse && null != detailResponse.error)
                        message = message.concat(detailResponse.error);
                    Log.e(TAG, "Error in fetching product list:" + message);
                    mEmptyListMessage.setText(message);
                    mEmptyListMessage.setVisibility(View.VISIBLE);
                    mProductListRecyclerView.setVisibility(View.INVISIBLE);
                    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
     * parsing response and setting product list
     */
    private void setProductList(ProductDetailResponse response) {
        int pageNum = response.pageNumber;
        List<ProductDetails> products = response.products;

        if(null != products) {
            Realm realm = RealmDatabase.getInstance().getRealm();
            for (ProductDetails details: products) {
                details.setPageNumber(pageNum);
                if(updateProductDB(realm, details)) {
                    mProductDetailsList.add(details);
                }
            }
            realm.close();
        }
    }

    /*
     * update db with product detail entry with unique product id
     */
    private boolean updateProductDB(Realm realm, ProductDetails details) {
            ProductDBModel model = new ProductDBModel();
            model.setProductId(details.getProductId());
            model.setProductName(details.getProductName());
            model.setLongDescription(details.getLongDescription());
            model.setShortDescription(details.getShortDescription());
            model.setInStock(details.isInStock());
            model.setPageNumber(details.getPageNumber());
            model.setPrice(details.getPrice());
            model.setProductImage(details.getProductImage());
            model.setReviewRating(details.getReviewRating());
            model.setReviewCount(details.getReviewCount());
            return new ProductDBWrapper().insertProductDetail(realm, model);
    }
}
