package com.example.rakeshrav.magicmovies.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.itunesData.Result;
import com.example.rakeshrav.magicmovies.ui.movieDetails.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MoviesViewHolder> {

    private Context context;
    public static final String URL = "https://m.media-amazon.com/images/M/MV5BMTY4MTUxMjQ5OV5BMl5BanBnXkFtZTcwNTUyMzg5Ng@@._V1_.jpg";

    public AdapterMovies(Context context, ArrayList<Result> results) {
        this.context = context;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, final int position) {
        Picasso.with(context).load(URL).fit().into(holder.ivMoviePoster);

        holder.cvForegroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MovieDetailsActivity.getStartIntent(context);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder {


        ImageView ivMoviePoster;
        CardView cvForegroundView;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            cvForegroundView = itemView.findViewById(R.id.cvForegroundView);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
        }
    }
}
