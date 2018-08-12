package com.example.rakeshrav.magicmovies.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.itunesData.ItunesData;
import com.example.rakeshrav.magicmovies.ui.base.BaseActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements SplashView {

    private static final String TAG = SplashActivity.class.getSimpleName();

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
    @BindView(R.id.tvSongsCount)
    TextView tvSongsCount;
    @BindView(R.id.llSongsCount)
    LinearLayout llSongsCount;
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

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

    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

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

        setUpNavDrawer();

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setAdapter(new AdapterMovies(this, null));
    }

    private void setUpNavDrawer() {

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                // close drawer when item is tapped
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    private void searchForSongs(final String s) {
        if (isNetworkConnected()) {
            mPresenter.getSongList(s, "");
        } else {
            showErrorDialog("No Internet Connection Available!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissErrDialog();
                    searchForSongs(s);
                }
            });
        }
    }


    @Override
    public void populateData(ItunesData itunesData) {

        if (itunesData.getResultCount() > 0) {


        } else {

        }
    }

    @OnClick(R.id.cvTopMoviesSplash)
    public void onViewClickedExplore() {
    }
}
