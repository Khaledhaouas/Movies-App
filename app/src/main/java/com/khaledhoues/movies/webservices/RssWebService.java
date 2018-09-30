package com.khaledhoues.movies.webservices;

import com.khaledhoues.movies.entities.RSSFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssWebService {


    @GET("https://movieweb.com/rss/movie-news/")
    Call<RSSFeed> loadRSSFeed();
}
