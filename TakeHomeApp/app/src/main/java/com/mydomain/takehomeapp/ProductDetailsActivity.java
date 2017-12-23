package com.mydomain.takehomeapp;
/*
 * Description: Product details activity
 * Created by jmonani on 12/21/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.mydomain.takehomeapp.services.apihelper.ProductDetails;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductDetails productDetails = getIntent().getParcelableExtra("extra_product_details");
        setContentView(R.layout.product_details_layout);
        TextView productNameTV = findViewById(R.id.product_detail_name);
        TextView priceTV = findViewById(R.id.product_detail_price);
        TextView descriptionTV = findViewById(R.id.product_detail_desc);
        TextView ratingTV = findViewById(R.id.product_detail_rating);
        if(null != productDetails) {
            // retrieve product details to update the ui
            Log.i("ProductDetailsActivity", "Product Details for:" + productDetails.getProductName());
            productNameTV.setText(Html.fromHtml(productDetails.getProductName()));
            priceTV.setText(productDetails.getPrice());
            if(null != productDetails.getShortDescription())
                descriptionTV.setText(Html.fromHtml(productDetails.getShortDescription()));
            ratingTV.setText("Rating: " + productDetails.getReviewRating());
        }
    }
}
