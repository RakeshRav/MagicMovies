package com.example.rakeshrav.magicmovies.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.rakeshrav.magicmovies.BuildConfig;
import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.Result;
import com.example.rakeshrav.magicmovies.ui.movieDetails.MovieDetailsActivity;
import com.example.rakeshrav.magicmovies.utility.ScreenUtils;
import com.example.rakeshrav.magicmovies.utility.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MoviesViewHolder> {

    public static final String URL = "https://m.media-amazon.com/images/M/MV5BMTY4MTUxMjQ5OV5BMl5BanBnXkFtZTcwNTUyMzg5Ng@@._V1_.jpg";
    private static final String TAG = AdapterMovies.class.getSimpleName();
    private Context context;
    private ArrayList<Result> results;

    public AdapterMovies(Context context, ArrayList<Result> results) {
        this.context = context;
        this.results = new ArrayList<>();
        if (results!= null){
            this.results.addAll(results);
        }
    }

    public void updateList(List<Result> results){
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false);

        MoviesViewHolder viewHolder = new MoviesViewHolder(itemView);

        int height = (ScreenUtils.getScreenHeight(context)- ViewUtils.dpToPx(90))/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

        viewHolder.flMainItem.setLayoutParams(params);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {

        String path = BuildConfig.IMAGE_BASE_URL.concat(results.get(position).getPosterPath());

        Picasso.with(context).load(path).fit().into(holder.ivMoviePoster);

        holder.cvForegroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MovieDetailsActivity.getStartIntent(context);
                intent.putExtra(MovieDetailsActivity.MOVIE_ID, String.valueOf(results.get(holder.getAdapterPosition()).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder {


        FrameLayout flMainItem;
        ImageView ivMoviePoster;
        CardView cvForegroundView;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            cvForegroundView = itemView.findViewById(R.id.cvForegroundView);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            flMainItem = itemView.findViewById(R.id.flMainItem);
        }
    }
}
