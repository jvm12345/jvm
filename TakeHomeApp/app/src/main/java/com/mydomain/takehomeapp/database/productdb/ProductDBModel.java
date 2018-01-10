package com.mydomain.takehomeapp.database.productdb;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*
 * Created by jmonani on 1/7/2018.
 */

public class ProductDBModel extends RealmObject {
    private String productName;
    private String price;
    private String shortDescription;
    private String longDescription;
    @Required
    @PrimaryKey
    private String productId;
    private String productImage;
    private String reviewRating;
    private Integer reviewCount;
    private Boolean inStock;
    private Integer pageNumber;

    /*
     * setter and getter methods
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getProductId() {
        return productId;
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

    public Boolean getInStock() {
        return inStock;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
}
