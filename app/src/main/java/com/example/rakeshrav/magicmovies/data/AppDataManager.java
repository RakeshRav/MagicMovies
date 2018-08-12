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

package com.example.rakeshrav.magicmovies.data;


import android.content.Context;

import com.example.rakeshrav.magicmovies.data.network.ApiHelper;
import com.example.rakeshrav.magicmovies.data.network.model.movieDetailsData.MovieDetailsData;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.data.network.model.searchData.SearchData;
import com.example.rakeshrav.magicmovies.data.prefs.PreferencesHelper;
import com.example.rakeshrav.magicmovies.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Callback;

/**
 * Created by rao on.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public MovieListData getLastResultData() {
        return mPreferencesHelper.getLastResultData();
    }

    @Override
    public void setLastResult(MovieListData results) {
        mPreferencesHelper.setLastResult(results);
    }

    @Override
    public void getMovieDetails(String movieId, String apiKey, Callback<MovieDetailsData> callback) {

    }

    @Override
    public void getMoviesList(String movieId, String apiKey, String withGenre, Callback<MovieListData> callback) {

    }

    @Override
    public void getSearchMovies(String query, String apiKey, Callback<SearchData> callback) {

    }
}
