package com.mydomain.takehomeapp.services;
/*
 * Description: product api retrofit interface using https://walmartlabs-test.appspot.com/
 * Created by jmonani on 12/21/17.
 */

import com.mydomain.takehomeapp.services.apihelper.ProductDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ProductApiInterface {

        @GET("walmartproducts/{api_key}/{page_num}/{page_size}")
        Call<ProductDetailResponse> getProductList(@Path("api_key") String apiKey,
                                                   @Path("page_num") String page,
                                                   @Path("page_size") String size);
}
