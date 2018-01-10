package com.mydomain.takehomeapp.services.apihelper;
/*
 * Description: Product details
 * Created by jmonani on 12/21/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDetails implements Parcelable {

    private String productName; // "VIZIO Class Full-Array LED Smart TV",
    private String price;
    private String shortDescription;
    private String longDescription;
    private String productId; // "31e1cb21-5504-4f02-885b-8f267131a93f",
    private String productImage; //"http://someurl/0084522601078_A",
    private String reviewRating;  //0.0
    private Integer reviewCount;   //0
    private Boolean inStock; //true
    private Integer pageNumber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(price);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(productId);
        dest.writeString(productImage);
        dest.writeString(reviewRating);
        dest.writeInt(reviewCount.intValue());
        boolean[] temp = new boolean[1];
        temp[0] = inStock.booleanValue();
        dest.writeBooleanArray(temp);
        dest.writeInt(pageNumber);
    }

    /**
     * Retrieving data from Parcel object
     **/
    private ProductDetails(Parcel in){
        this.productName = in.readString();
        this.price = in.readString();
        this.shortDescription = in.readString();
        this.longDescription= in.readString();
        this.productId = in.readString();
        this.productImage = in.readString();
        this.reviewRating = in.readString();
        this.reviewCount = in.readInt();
        boolean[] temp = new boolean[1];
        in.readBooleanArray(temp);
        this.inStock = temp[0];
        this.pageNumber = in.readInt();
    }

    public static final Parcelable.Creator<ProductDetails> CREATOR = new Parcelable.Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel source) {
            return new ProductDetails(source);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };

    /*
     * get and set methods
     */
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getPrice() {
        return price;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public Boolean isInStock() {
        return inStock;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
