package com.mydomain.takehomeapp;
/*
 * Description: Product details activity for detail screen
 * Created by jmonani on 12/21/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydomain.takehomeapp.services.apihelper.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    int mSize;
    private List<ProductDetails> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSize = HomeApplication.getInstance().getProductList().size();
        mProductList = HomeApplication.getInstance().getProductList();
        setContentView(R.layout.product_details_activity);
        ViewPager viewPager = findViewById(R.id.product_pager);
        viewPager.setAdapter(new MyAdapter(this));
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
            ProductDetails productDetails = mProductList.get(position);
            if(null != productDetails) {
                // retrieve product details to update the ui
                Log.i("ProductDetailsActivity", "Product Details for:" + productDetails.getProductName());
                productNameTV.setText(Html.fromHtml(productDetails.getProductName()));
                priceTV.setText(productDetails.getPrice());
                if (null != productDetails.getShortDescription())
                    descriptionTV.setText(Html.fromHtml(productDetails.getShortDescription()));
                String rating = getString(R.string.product_detail_rating_label);
                rating = rating.concat(" : ").concat(productDetails.getReviewRating());
                ratingTV.setText(rating);
                Picasso.with(context).load(productDetails.productImage).into(imageView);
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
