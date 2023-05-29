package com.example.newsdesk.rests;

import com.example.newsdesk.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("top-headlines")
    Call< ResponseModel > getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);
}
