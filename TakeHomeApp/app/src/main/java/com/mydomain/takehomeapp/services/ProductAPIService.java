package com.mydomain.takehomeapp.services;

/*
 * Description: product api service using Retrofit
 * Created by jmonani on 12/21/17.
 */

import com.mydomain.takehomeapp.services.apihelper.ProductDetailResponse;
import com.mydomain.takehomeapp.utility.AsyncCallbackInf;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAPIService {

    // api url and key
    public static final String BASE_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/";
    public static final String API_KEY = "97f633a0-7d0a-4026-abf7-fb66d5b887ad";
    public static final String PAGE_SIZE = "30";

    /*
     * create rest adapter
     */
    public static Retrofit getRestAdapter() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter;
    }

    public void getProductListAsync(int page, final AsyncCallbackInf callbackInf) {

        ProductApiInterface productApiInterface =
                getRestAdapter().create(ProductApiInterface.class);

            Call<ProductDetailResponse> call = productApiInterface.getProductList(API_KEY, String.valueOf(page), PAGE_SIZE);
            call.enqueue(new Callback<ProductDetailResponse>() {
                @Override
                public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                    if(response.isSuccessful()) {
                        ProductDetailResponse productDetailResponse = response.body();
                        callbackInf.onResponseCallback(0, productDetailResponse);
                    } else {
                        ProductDetailResponse failureResponse = new ProductDetailResponse();
                        failureResponse.error = response.message();
                        callbackInf.onResponseCallback(1, failureResponse);
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailResponse> call, Throwable throwable) {
                    ProductDetailResponse failureResponse = new ProductDetailResponse();
                    failureResponse.error = throwable.getLocalizedMessage();
                    callbackInf.onResponseCallback(1, failureResponse);
                }
            });
    }
}
