package com.st18apps.newsapp.rest;

import com.st18apps.newsapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);
}
