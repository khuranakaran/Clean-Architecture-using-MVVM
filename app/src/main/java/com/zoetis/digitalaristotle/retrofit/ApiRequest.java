package com.zoetis.digitalaristotle.retrofit;

import com.zoetis.digitalaristotle.model.Assessment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("v2/everything/")
    Call<Assessment> getMovieArticles();
}