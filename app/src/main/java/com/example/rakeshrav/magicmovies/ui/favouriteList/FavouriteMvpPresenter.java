package com.example.rakeshrav.magicmovies.ui.favouriteList;

import com.example.rakeshrav.magicmovies.data.network.model.itunesData.Result;
import com.example.rakeshrav.magicmovies.di.PerActivity;
import com.example.rakeshrav.magicmovies.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface FavouriteMvpPresenter<V extends FavouriteView> extends MvpPresenter<V> {
    List<Result> getFavResults();
    void removeFavSongs(Result result);
}
