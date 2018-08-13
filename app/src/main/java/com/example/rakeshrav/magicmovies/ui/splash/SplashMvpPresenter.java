package com.example.rakeshrav.magicmovies.ui.splash;

import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.di.PerActivity;
import com.example.rakeshrav.magicmovies.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashView> extends MvpPresenter<V> {

    void getMoviesList(String listType, String genres);

    void searchMovies(String queryTerm);

    void saveLastResults(MovieListData data);

    void saveDbCollection(MovieListData data);

    void getDbCollection();

    MovieListData getMovieListData();
}
