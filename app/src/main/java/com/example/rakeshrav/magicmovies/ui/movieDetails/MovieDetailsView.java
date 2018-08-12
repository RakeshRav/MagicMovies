package com.example.rakeshrav.magicmovies.ui.movieDetails;

import com.example.rakeshrav.magicmovies.data.network.model.itunesData.ItunesData;
import com.example.rakeshrav.magicmovies.data.network.model.movieDetailsData.MovieDetailsData;
import com.example.rakeshrav.magicmovies.ui.base.MvpView;

public interface MovieDetailsView extends MvpView {

    void populateData(MovieDetailsData movieDetailsData);
}
