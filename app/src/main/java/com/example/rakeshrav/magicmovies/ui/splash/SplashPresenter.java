package com.example.rakeshrav.magicmovies.ui.splash;

import com.example.rakeshrav.magicmovies.utility.Log;
import android.view.View;

import com.example.rakeshrav.magicmovies.BuildConfig;
import com.example.rakeshrav.magicmovies.data.DataManager;
import com.example.rakeshrav.magicmovies.data.db.model.movieData.Result;
import com.example.rakeshrav.magicmovies.data.network.RestClient;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.data.network.model.searchData.SearchData;
import com.example.rakeshrav.magicmovies.ui.base.BasePresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SplashPresenter<V extends SplashView> extends BasePresenter<V> implements SplashMvpPresenter<V> {


    private static final String TAG = SplashPresenter.class.getSimpleName();

    @Inject
    public SplashPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMoviesList(final String listType, final String genres) {
        Log.d(TAG,"listType : "+listType+" geners : "+genres);
        getMvpView().showLoading();
        RestClient.getApiServicePojo().getMoviesList(listType, BuildConfig.API_KEY,
                genres,
                new Callback<MovieListData>() {
            @Override
            public void success(MovieListData movieListData, Response response) {
                Log.d(TAG, "movie list success : " + new Gson().toJson(movieListData));
                getMvpView().hideLoading();
                getMvpView().populateData(movieListData.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "movie list failure : " + error.toString());
                getMvpView().hideLoading();
                getMvpView().showErrorDialog("Something went wrong at our end!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMvpView().dismissErrDialog();
                        getMoviesList(listType, genres);
                    }
                });
            }
        });
    }


    @Override
    public void searchMovies(String queryTerm) {
        RestClient.getApiServicePojo().getSearchMovies(queryTerm, BuildConfig.API_KEY, new Callback<SearchData>() {
            @Override
            public void success(SearchData searchData, Response response) {
                Log.d(TAG, "movie search success : " + new Gson().toJson(searchData));
                getMvpView().populateData(searchData.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "movie search fail : " + error.toString());
            }
        });
    }

    @Override
    public void saveLastResults(MovieListData data) {
        getDataManager().setLastResult(data);
    }

    @Override
    public void saveDbCollection(MovieListData data) {

        ArrayList<Result> results = new ArrayList<>();
        for (com.example.rakeshrav.magicmovies.data.network.model.movieListData.Result result: data.getResults()){
            Result result1 = new Result();
            result1.setCollectionId(0L);
            result1.setPosterPath(result.getPosterPath());
            results.add(result1);
        }

        getDataManager().saveCollectionList(results);

        Log.d(TAG,"results saved in db");
    }

    @Override
    public void getDbCollection() {
        getCompositeDisposable().add(getDataManager()
                .getAllResults()
                .subscribeOn(new IoScheduler())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (results != null) {
                            Log.d(TAG,"results : "+results.size());
                        }
                    }
                }));
    }


    @Override
    public MovieListData getMovieListData() {
        return getDataManager().getLastResultData();
    }
}
