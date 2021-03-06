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

package com.example.rakeshrav.magicmovies.di.component;

import android.app.Application;
import android.content.Context;

import com.example.rakeshrav.magicmovies.MagicMoviesApp;
import com.example.rakeshrav.magicmovies.data.DataManager;
import com.example.rakeshrav.magicmovies.di.ApplicationContext;
import com.example.rakeshrav.magicmovies.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rao .
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MagicMoviesApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}