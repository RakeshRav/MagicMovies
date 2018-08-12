package com.example.rakeshrav.magicmovies.ui.splash;

import android.util.Log;
import android.view.View;

import com.example.rakeshrav.magicmovies.BuildConfig;
import com.example.rakeshrav.magicmovies.data.DataManager;
import com.example.rakeshrav.magicmovies.data.network.RestClient;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.ui.base.BasePresenter;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
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
    public void getMoviesList(final String listType) {
        getMvpView().showLoading();
        RestClient.getApiServicePojo().getMoviesList(listType, BuildConfig.API_KEY, new Callback<MovieListData>() {
            @Override
            public void success(MovieListData movieListData, Response response) {
                Log.d(TAG, "movie list success : " + new Gson().toJson(movieListData));
                getMvpView().hideLoading();
                getMvpView().populateData(movieListData);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "movie list failure : " + error.toString());
                getMvpView().hideLoading();
                getMvpView().showErrorDialog("Something went wrong at our end!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMvpView().dismissErrDialog();
                        getMoviesList(listType);
                    }
                });
            }
        });
    }
}
