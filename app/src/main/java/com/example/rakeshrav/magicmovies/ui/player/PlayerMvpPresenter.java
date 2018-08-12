package com.example.rakeshrav.magicmovies.ui.player;

import com.example.rakeshrav.magicmovies.data.network.model.itunesData.Result;
import com.example.rakeshrav.magicmovies.di.PerActivity;
import com.example.rakeshrav.magicmovies.ui.base.MvpPresenter;

@PerActivity
public interface PlayerMvpPresenter<V extends PlayerView> extends MvpPresenter<V> {
    void setFavSongs(Result result);
    void removeFavSongs(Result result);
    boolean checkSongIsFav(Result result);
}
