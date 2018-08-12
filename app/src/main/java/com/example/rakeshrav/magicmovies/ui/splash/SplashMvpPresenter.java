package com.example.rakeshrav.magicmovies.ui.splash;

import com.example.rakeshrav.magicmovies.di.PerActivity;
import com.example.rakeshrav.magicmovies.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashView> extends MvpPresenter<V> {

    void getSongList(String term, String limit);
}
