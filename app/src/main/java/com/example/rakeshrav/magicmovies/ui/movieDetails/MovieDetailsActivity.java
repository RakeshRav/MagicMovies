package com.example.rakeshrav.magicmovies.ui.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.example.rakeshrav.magicmovies.utility.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshrav.magicmovies.BuildConfig;
import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.movieDetailsData.MovieDetailsData;
import com.example.rakeshrav.magicmovies.ui.base.BaseActivity;
import com.example.rakeshrav.magicmovies.utility.CommonUtils;
import com.example.rakeshrav.magicmovies.utility.ScreenUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsView {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    public static final String MOVIE_ID = "movieId";

    @Inject
    MovieDetailsMvpPresenter<MovieDetailsView> mPresenter;
    @BindView(R.id.ivBackgroundImage)
    ImageView ivBackgroundImage;
    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.llRating)
    LinearLayout llRating;
    @BindView(R.id.tvVotes)
    TextView tvVotes;
    @BindView(R.id.rlRatingContainer)
    RelativeLayout rlRatingContainer;
    @BindView(R.id.tvReleaseStatus)
    TextView tvReleaseStatus;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.tvLanguage)
    TextView tvLanguage;
    @BindView(R.id.tvDrama)
    TextView tvDrama;
    @BindView(R.id.tvViewDetails)
    TextView tvViewDetails;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils.setHideWindow(this);

        setContentView(R.layout.activity_movie_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

    }

    @Override
    protected void setUp() {
        Intent intent = getIntent();
        String movieId = intent.getStringExtra(MOVIE_ID);
        Log.d(TAG,"Movie Id : "+movieId);
        getMoviesDetails(movieId);
    }

    private void getMoviesDetails(final String movieId){
        if (isNetworkConnected()){
            mPresenter.getMovieDetails(movieId);
        }else {
            showErrorDialog("No Internet Connection!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissErrDialog();
                    getMoviesDetails(movieId);
                }
            });
        }
    }

    MovieDetailsData movieDetailsData;
    @Override
    public void populateData(MovieDetailsData movieDetailsData) {
        Log.d(TAG, "populate data in view : " + movieDetailsData.getId());

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getPosterPath())) {
            String url = BuildConfig.IMAGE_BASE_URL.concat(movieDetailsData.getPosterPath());
            Picasso.with(this).load(url).fit().into(ivBackgroundImage);
        }
        tvMovieTitle.setText(movieDetailsData.getTitle());

        tvDesc.setText(movieDetailsData.getOverview());

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getStatus())) {
            tvReleaseStatus.setText(movieDetailsData.getStatus());
        }

        if (!movieDetailsData.getSpokenLanguages().isEmpty()){
            if (!CommonUtils.isNullOrEmpty(movieDetailsData.getSpokenLanguages().get(0).getName())) {
                tvLanguage.setText(movieDetailsData.getSpokenLanguages().get(0).getName());
            }
        }

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getReleaseDate())) {
            tvReleaseDate.setText(CommonUtils.getFormatDate(movieDetailsData.getReleaseDate()));
        }

        String genres = "";
        for (int i = 0; i< movieDetailsData.getGenres().size(); i++){
            if (!CommonUtils.isNullOrEmpty(movieDetailsData.getGenres().get(i).getName())) {
                if (i!=movieDetailsData.getGenres().size()-1){
                    genres = genres.concat(movieDetailsData.getGenres().get(i).getName()+", ");
                }else {
                    genres = genres.concat(movieDetailsData.getGenres().get(i).getName());
                }
            }
        }
        tvDrama.setText(genres);

        tvRating.setText(String.valueOf(movieDetailsData.getVoteAverage()));
        tvVotes.setText(String.valueOf(movieDetailsData.getVoteCount()).concat(" votes"));

        if(movieDetailsData.getRuntime()  != null) {
            tvDuration.setText(CommonUtils.getFormatedDuration(movieDetailsData.getRuntime()));
        }
        this.movieDetailsData = movieDetailsData;
        if (movieDetailsData.getHomepage() == null){
            tvViewDetails.setVisibility(View.GONE);
        }else {
            tvViewDetails.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tvViewDetails)
    public void onViewClicked() {
        Log.d(TAG,"url : "+movieDetailsData.getHomepage());

        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieDetailsData.getHomepage()));
            startActivity(intent);
        }catch (Exception e){
//            Log.d(TAG,"ex : "+);
            Toast.makeText(this,"No Information Availabel", Toast.LENGTH_LONG).show();
        }
    }
}
