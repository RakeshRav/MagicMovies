package com.example.rakeshrav.magicmovies.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakeshrav.magicmovies.R;
import com.example.rakeshrav.magicmovies.data.network.model.itunesData.ItunesData;
import com.example.rakeshrav.magicmovies.data.network.model.itunesData.Result;
import com.example.rakeshrav.magicmovies.ui.base.BaseActivity;
import com.example.rakeshrav.magicmovies.ui.favouriteList.FavouriteActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tvSongsCount)
    TextView tvSongsCount;
    @BindView(R.id.llSongsCount)
    LinearLayout llSongsCount;
    @BindView(R.id.viewPagerItems)
    ViewPager viewPagerItems;
    @BindView(R.id.llIndicators)
    LinearLayout llIndicators;
    int numberItemToFit = 0;
    int currentIndicator = 0;
    int prevIndicator = 0;
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
    private SearchResultsPagerAdapter pagerAdapter;

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

            tvSongsCount.setText("  " + String.valueOf(itunesData.getResultCount()));

            int pages = itunesData.getResultCount() / numberItemToFit;

            if (itunesData.getResultCount() % numberItemToFit != 0) {
                pages++;
            }

            viewPagerItems.setVisibility(View.VISIBLE);
            llIndicators.setVisibility(View.VISIBLE);
            llPlaceHolder.setVisibility(View.INVISIBLE);
            viewPagerItems.setOffscreenPageLimit(pages);
            pagerAdapter = new SearchResultsPagerAdapter(getSupportFragmentManager(), pages, itunesData);

            viewPagerItems.setAdapter(pagerAdapter);

            viewPagerItems.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    updateIndicators(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {
            Snackbar.make(llMainActivity, "No Results found!", 2000);
            tvSongsCount.setText("  0");
            llPlaceHolder.setVisibility(View.VISIBLE);
            tvPlaceholder.setText("No Results found, Taste some other search");
            viewPagerItems.setVisibility(View.GONE);
            llIndicators.setVisibility(View.INVISIBLE);
        }
    }

    private void updateIndicators(int pos) {

        prevIndicator = currentIndicator;
        currentIndicator = pos;

        ImageView imageView = (ImageView) llIndicators.getChildAt(prevIndicator);
        imageView.setImageResource(R.drawable.rectangle_4_copy);

        ImageView imageViewCurr = (ImageView) llIndicators.getChildAt(currentIndicator);
        imageViewCurr.setImageResource(R.drawable.rectangle_4);
    }

    @OnClick(R.id.cvTopMoviesSplash)
    public void onViewClickedExplore() {
    }

    private class SearchResultsPagerAdapter extends FragmentStatePagerAdapter {

        private int pages;
        private ItunesData itunesData;
        private int start = 0;

        SearchResultsPagerAdapter(FragmentManager fm, int pages, ItunesData itunesData) {
            super(fm);
            this.pages = pages;
            this.itunesData = itunesData;
        }

        @Override
        public Fragment getItem(int position) {

            ArrayList<Result> results = getListItems();
            return FragmentSearchResult.getInstance(numberItemToFit, results);
        }

        ArrayList<Result> getListItems() {

            ArrayList<Result> results = new ArrayList<>();

            List<Result> itunesResult = itunesData.getResults();

            int end = numberItemToFit + start;

            if (end > itunesResult.size()) {
                end = itunesResult.size();
            }

            for (int i = start; i < end; i++) {
                results.add(itunesResult.get(start));
                start++;
            }
            return results;
        }

        @Override
        public int getCount() {
            return pages;
        }
    }
}
