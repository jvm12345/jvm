package com.mydomain.takehomeapp.ProductCatalogue;

/*
 * Created by jmonani on 1/7/2018.
 * product list adapter for list of product
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydomain.takehomeapp.MainActivity;
import com.mydomain.takehomeapp.R;
import com.mydomain.takehomeapp.services.apihelper.ProductDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private ArrayList<ProductDetails> mProductDetdailsDataSet;
    private MainActivity mActivity;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private ImageView productThumbImage;

        MyViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.product_name);
            this.productPrice = itemView.findViewById(R.id.product_price);
            this.productThumbImage = itemView.findViewById(R.id.product_list_thumb_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    ProductDetails details = mProductDetdailsDataSet.get(index);
                    Intent intent = new Intent(mActivity, ProductDetailsActivity.class);
                    intent.putExtra("extra_product_detail_index", index);
                    mActivity.startActivity(intent);
                }
            });

        }
    }

    public ProductListAdapter(ArrayList<ProductDetails> data, MainActivity activity) {
        this.mProductDetdailsDataSet = data;
        mActivity = activity;
    }

    public void setData(ArrayList<ProductDetails> data) {
        this.mProductDetdailsDataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productCardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cards_layout, parent, false);

        return new MyViewHolder(productCardView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.productName;
        TextView textViewVersion = holder.productPrice;
        ImageView thumImage = holder.productThumbImage;

        textViewName.setText(mProductDetdailsDataSet.get(listPosition).getProductName());
        textViewVersion.setText(mProductDetdailsDataSet.get(listPosition).getPrice());
        Picasso.with(mActivity).load(mProductDetdailsDataSet.get(listPosition).getProductImage()).into(thumImage);
    }

    @Override
    public int getItemCount() {
        return mProductDetdailsDataSet.size();
    }
}
