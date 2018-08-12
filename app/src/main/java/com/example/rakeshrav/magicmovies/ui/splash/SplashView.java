package com.example.rakeshrav.magicmovies.ui.splash;

import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.Result;
import com.example.rakeshrav.magicmovies.ui.base.MvpView;

import java.util.List;

public interface SplashView extends MvpView {

    void populateData(List<Result> results);
}
