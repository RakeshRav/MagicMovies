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

package com.example.rakeshrav.magicmovies.di.module;

import android.app.Application;
import android.content.Context;

import com.example.rakeshrav.magicmovies.data.AppDataManager;
import com.example.rakeshrav.magicmovies.data.DataManager;
import com.example.rakeshrav.magicmovies.data.db.AppDbHelper;
import com.example.rakeshrav.magicmovies.data.db.DbHelper;
import com.example.rakeshrav.magicmovies.data.network.ApiHelper;
import com.example.rakeshrav.magicmovies.data.network.AppApiHelper;
import com.example.rakeshrav.magicmovies.data.prefs.AppPreferencesHelper;
import com.example.rakeshrav.magicmovies.data.prefs.PreferencesHelper;
import com.example.rakeshrav.magicmovies.di.ApiInfo;
import com.example.rakeshrav.magicmovies.di.ApplicationContext;
import com.example.rakeshrav.magicmovies.di.DatabaseInfo;
import com.example.rakeshrav.magicmovies.di.PreferenceInfo;
import com.example.rakeshrav.magicmovies.utility.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rao .
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "provideApiKeyHere";
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }
}
