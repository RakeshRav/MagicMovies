package com.example.rakeshrav.magicmovies.ui.movieDetails;

import com.example.rakeshrav.magicmovies.utility.Log;
import android.view.View;

import com.example.rakeshrav.magicmovies.BuildConfig;
import com.example.rakeshrav.magicmovies.data.DataManager;
import com.example.rakeshrav.magicmovies.data.network.RestClient;
import com.example.rakeshrav.magicmovies.data.network.model.movieDetailsData.MovieDetailsData;
import com.example.rakeshrav.magicmovies.ui.base.BasePresenter;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MovieDetailsPresenter<V extends MovieDetailsView> extends BasePresenter<V> implements MovieDetailsMvpPresenter<V> {


    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    @Inject
    public MovieDetailsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMovieDetails(final String movieId) {
        getMvpView().showLoading();
        RestClient.getApiServicePojo().getMovieDetails(movieId,
                BuildConfig.API_KEY, new Callback<MovieDetailsData>() {
                    @Override
                    public void success(MovieDetailsData movieDetailsData, Response response) {
                        Log.d(TAG, "Movie details success : " + new Gson().toJson(movieDetailsData));
                        getMvpView().populateData(movieDetailsData);
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "Movie details failure : " + error.toString());
                        getMvpView().hideLoading();
                        getMvpView().showErrorDialog("Something went wrong at our side!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getMvpView().dismissErrDialog();
                                getMovieDetails(movieId);
                            }
                        });
                    }
                });
    }
}
