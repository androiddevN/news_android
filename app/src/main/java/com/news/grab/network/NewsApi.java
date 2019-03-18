package com.news.grab.network;

import com.news.grab.model.ArticleResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * An Api interface to send network requests
 * Includes Category enum that provides category names for requests
 */
public interface NewsApi {
    String API_KEY = "3f358b79a3c044b4b4151c1864224a03";

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/top-headlines")
    Call<ArticleResponseWrapper> getHeadlines(
            @Query("category") String category,
            @Query("country") String country
    );

    enum Category {
        business("Business"),
        entertainment("Entertainment"),
        general("General"),
        health("Health"),
        science("Science"),
        sports("Sports"),
        technology("Technology");

        public final String title;

        Category(String title) {
            this.title = title;
        }
    }
}