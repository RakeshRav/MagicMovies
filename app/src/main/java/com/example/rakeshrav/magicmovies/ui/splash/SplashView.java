package com.example.rakeshrav.magicmovies.ui.splash;

import com.example.rakeshrav.magicmovies.data.network.model.itunesData.ItunesData;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.ui.base.MvpView;

public interface SplashView extends MvpView {

    void populateData(MovieListData movieListData);
}
