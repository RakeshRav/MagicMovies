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

package com.example.rakeshrav.magicmovies.data.db;

import com.example.rakeshrav.magicmovies.data.db.model.movieData.DaoMaster;
import com.example.rakeshrav.magicmovies.data.db.model.movieData.DaoSession;
import com.example.rakeshrav.magicmovies.data.db.model.movieData.Result;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<List<Result>> getAllResults() {
        return Observable.fromCallable(new Callable<List<Result>>() {
            @Override
            public List<Result> call() throws Exception {
                return mDaoSession.getResultDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> saveCollectionList(final List<Result> results) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getResultDao().insertInTx(results);
                return true;
            }
        });
    }
}
