package com.mydomain.takehomeapp.ProductCatalogue;

/*
 * product list adapter for list of product
 */
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydomain.takehomeapp.MainActivity;
import com.mydomain.takehomeapp.ProductDetailsActivity;
import com.mydomain.takehomeapp.R;
import com.mydomain.takehomeapp.services.apihelper.ProductDetails;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private ArrayList<ProductDetails> mProductDetdailsDataSet;
    private MainActivity mAactivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.product_name);
            this.productPrice = (TextView) itemView.findViewById(R.id.product_price);
            if(null != itemView) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = getAdapterPosition();
                        Log.i("ProductAdapter", "Selected Product: " + mProductDetdailsDataSet.get(index).getProductName());
                        ProductDetails details = mProductDetdailsDataSet.get(index);
                        Intent intent = new Intent(mAactivity, ProductDetailsActivity.class);
                        intent.putExtra("extra_product_details", details);
                        mAactivity.startActivity(intent);
                    }
                });
            }
        }
    }

    public ProductListAdapter(ArrayList<ProductDetails> data, MainActivity activity) {
        this.mProductDetdailsDataSet = data;
        mAactivity = activity;
    }

    public void setData(ArrayList<ProductDetails> data) {
        this.mProductDetdailsDataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productCardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cards_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(productCardView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.productName;
        TextView textViewVersion = holder.productPrice;

        textViewName.setText(mProductDetdailsDataSet.get(listPosition).getProductName());
        textViewVersion.setText(mProductDetdailsDataSet.get(listPosition).getPrice());
    }

    @Override
    public int getItemCount() {
        return mProductDetdailsDataSet.size();
    }
}
