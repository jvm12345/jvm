package com.mydomain.takehomeapp.services.apihelper;
/*
 * Description: 
 * Created by jmonani on 12/21/17.
 */

import java.util.List;

public class ProductDetailResponse extends BaseApiResponse {

    public List<ProductDetails> products;
    public String totalProducts;
    public Integer pageNumber;
    public String pageSize;
    public Integer status;
    public String kind;
    public String etag;
}
