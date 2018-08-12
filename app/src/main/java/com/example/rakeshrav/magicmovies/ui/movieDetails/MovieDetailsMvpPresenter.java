package com.example.rakeshrav.magicmovies.ui.movieDetails;

import com.example.rakeshrav.magicmovies.di.PerActivity;
import com.example.rakeshrav.magicmovies.ui.base.MvpPresenter;

@PerActivity
public interface MovieDetailsMvpPresenter<V extends MovieDetailsView> extends MvpPresenter<V> {

    void getMovieDetails(String movieId);
}
