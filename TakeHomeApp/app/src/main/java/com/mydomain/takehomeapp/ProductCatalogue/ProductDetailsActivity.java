package com.mydomain.takehomeapp.ProductCatalogue;
/*
 * Description: Product details activity for detail screen
 * Created by jmonani on 12/21/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydomain.takehomeapp.R;
import com.mydomain.takehomeapp.database.RealmDatabase;
import com.mydomain.takehomeapp.database.productdb.ProductDBModel;
import com.mydomain.takehomeapp.database.productdb.ProductDBWrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ProductDetailsActivity extends AppCompatActivity {
    private int mSize;
    private List<ProductDBModel> mProductModelList;
    private ViewPager mViewPager;
    private PagerAdapter mPageAdapter;
    private int mCurrentPage;
    private Realm mProductDBRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSize = 0;
        mCurrentPage = 0;
        if(null != getIntent()) {
            mCurrentPage = getIntent().getIntExtra("extra_product_detail_index", 0);
        }
        setContentView(R.layout.product_details_activity);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        if(null != toolbar) {
            setSupportActionBar(toolbar);
            if(null != getSupportActionBar()) {
                getSupportActionBar().setHomeButtonEnabled(true);
                setTitle(getString(R.string.product_detail_screen));
            }
            toolbar.setNavigationIcon(R.mipmap.ic_back_navigation);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        mViewPager = findViewById(R.id.product_pager);
        mPageAdapter = new MyAdapter(this);
        mViewPager.setAdapter(mPageAdapter);
        mProductDBRealm = RealmDatabase.getInstance().getRealm();
        RealmResults<ProductDBModel> productDBModelRealmResults = new ProductDBWrapper().retrieveAllProductRecordsAsync(mProductDBRealm);
        productDBModelRealmResults.addChangeListener(new RealmChangeListener<RealmResults<ProductDBModel>>() {
            @Override
            public void onChange(@NonNull RealmResults<ProductDBModel> productDBModels) {
                mSize = productDBModels.size();
                mProductModelList = mProductDBRealm.copyFromRealm(productDBModels);
                mViewPager.setCurrentItem(mCurrentPage);
                mPageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProductDBRealm.removeAllChangeListeners();
        mProductDBRealm.close();
    }


    public class MyAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private Context context;

        MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View rootView = inflater.inflate(R.layout.product_details_layout, view, false);
            TextView productNameTV = rootView.findViewById(R.id.product_detail_name);
            TextView priceTV = rootView.findViewById(R.id.product_detail_price);
            TextView descriptionTV = rootView.findViewById(R.id.product_detail_desc);
            TextView ratingTV = rootView.findViewById(R.id.product_detail_rating);
            ImageView imageView = rootView.findViewById(R.id.product_detail_image);
            if(position < mProductModelList.size()) {
                ProductDBModel productDBModel = mProductModelList.get(position);
                if (null != productDBModel) {
                    // retrieve product details and update the ui
                    productNameTV.setText(Html.fromHtml(productDBModel.getProductName()));
                    priceTV.setText(productDBModel.getPrice());
                    if (null != productDBModel.getShortDescription())
                        descriptionTV.setText(Html.fromHtml(productDBModel.getShortDescription()));
                    String rating = getString(R.string.product_detail_rating_label);
                    rating = rating.concat(" : ").concat(productDBModel.getReviewRating());
                    ratingTV.setText(rating);
                    Picasso.with(context).load(productDBModel.getProductImage()).into(imageView);
                }
            }

            view.addView(rootView, 0);
            return rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
