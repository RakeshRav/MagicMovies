/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.rakeshrav.magicmovies.data.network;

import com.example.rakeshrav.magicmovies.data.network.model.movieDetailsData.MovieDetailsData;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.data.network.model.searchData.SearchData;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by rao .
 */

public interface ApiHelper {

    @GET("/movie/{movieId}")
    void getMovieDetails(@Path("movieId") String movieId,
                         @Query("api_key") String apiKey,
                         Callback<MovieDetailsData> callback);

    @GET("/discover/movie")
    void getMoviesList(@Query("sort_by") String sortBy,
                       @Query("api_key") String apiKey,
                       @Query("with_genres") String withGenre,
                       Callback<MovieListData> callback);

    @GET("/search/movie")
    void getSearchMovies(@Query("query") String query,
                       @Query("api_key") String apiKey,
                       Callback<SearchData> callback);

}
