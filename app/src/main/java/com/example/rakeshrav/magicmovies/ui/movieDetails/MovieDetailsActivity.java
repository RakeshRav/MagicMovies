package com.example.rakeshrav.magicmovies.ui.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        mPresenter.getMovieDetails(550);
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

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getSpokenLanguages().get(0).getName())) {
            tvLanguage.setText(movieDetailsData.getSpokenLanguages().get(0).getName());
        }

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getReleaseDate())) {
            tvReleaseDate.setText(CommonUtils.getFormatDate(movieDetailsData.getReleaseDate()));
        }

        if (!CommonUtils.isNullOrEmpty(movieDetailsData.getGenres().get(0).getName())) {
            tvDrama.setText(movieDetailsData.getGenres().get(0).getName());
        }

        tvRating.setText(String.valueOf(movieDetailsData.getVoteAverage()));
        tvVotes.setText(String.valueOf(movieDetailsData.getVoteCount()).concat(" votes"));

        tvDuration.setText(getFormatedDuration(movieDetailsData.getRuntime()));
        this.movieDetailsData = movieDetailsData;
    }

    private String getFormatedDuration(int runtime) {
        int hour = runtime/60;
        while (runtime > 0){
            runtime -= 60;
        }

        int min = runtime+60;

        return hour+"h "+min+"min";
    }

    @OnClick(R.id.tvViewDetails)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieDetailsData.getHomepage()));
        startActivity(intent);
    }
}
