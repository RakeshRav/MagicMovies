package com.example.rakeshrav.magicmovies.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.movieListData.MovieListData;
import com.example.rakeshrav.magicmovies.ui.base.BaseActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements SplashView {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final String POPULAR_MOVIES = "popularity.desc";
    private static final String TOP_MOVIES = "vote_average.desc";

    @Inject
    SplashMvpPresenter<SplashView> mPresenter;
    @BindView(R.id.cvSearchSplash)
    CardView cvSearchSplash;
    @BindView(R.id.flMainSpalsh)
    FrameLayout flMainSpalsh;
    @BindView(R.id.llMainActivity)
    LinearLayout llMainActivity;
    @BindView(R.id.tvPlaceholder)
    TextView tvPlaceholder;
    @BindView(R.id.llPlaceHolder)
    LinearLayout llPlaceHolder;
    @BindView(R.id.cvTopMoviesSplash)
    CardView cvTopMoviesSplash;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.tvAppName)
    TextView tvAppName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    private AdapterMovies adapterMovies;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.setVoiceSearch(false);

        return true;
    }

    private ActionBar actionbar;
    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        flMainSpalsh.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                flMainSpalsh.startAnimation(fadeOut);

                llMainActivity.setVisibility(View.VISIBLE);
                Animation fadein = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in_normal);
                llMainActivity.startAnimation(fadein);
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMoviesList(POPULAR_MOVIES);
            }
        },2000);

        setUpNavDrawer();

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));

        adapterMovies = new AdapterMovies(this, null);
        rvMovies.setAdapter(adapterMovies);

        actionbar.setTitle("Popular Movies");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpNavDrawer() {

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                switch (item.getItemId()){
                    case R.id.search:
                        actionbar.setTitle("Search Movies");
                        return true;
                    case R.id.top:
                        actionbar.setTitle("Top Movies");
                        mPresenter.getMoviesList(TOP_MOVIES);
                        return true;
                    case R.id.popular:
                        actionbar.setTitle("Top Movies");
                        mPresenter.getMoviesList(POPULAR_MOVIES);
                        return true;
                }
                return true;
            }
        });

        navView.getMenu().getItem(0).setChecked(true);
    }

    private void searchForMovies(final String term) {
        if (isNetworkConnected()) {

        } else {
            showErrorDialog("No Internet Connection Available!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissErrDialog();
                    searchForMovies(term);
                }
            });
        }
    }

    @Override
    public void populateData(MovieListData movieListData) {
        Log.d(TAG, "PopulateData in UI");
        if (movieListData.getResults().size() > 0) {
            llPlaceHolder.setVisibility(View.INVISIBLE);
            rvMovies.setVisibility(View.VISIBLE);
            if (adapterMovies.getItemCount()>0){
                rvMovies.smoothScrollToPosition(0);
            }
            adapterMovies.updateList(movieListData.getResults());
        } else {
            rvMovies.setVisibility(View.INVISIBLE);
            llPlaceHolder.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.cvTopMoviesSplash)
    public void onViewClickedExplore() {
    }


}
