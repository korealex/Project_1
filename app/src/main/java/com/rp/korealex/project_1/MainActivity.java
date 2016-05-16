package com.rp.korealex.project_1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GridImageAdapter.Callback, MovieGridFragment.Callback, TrailerAdapter.TCallback {


    private boolean mTwoPane;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static Context context;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.context = getApplicationContext();


        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_details_container) != null) {

            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }


    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setLogo(R.drawable.logo_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onItemSelected(Movie movie) {

        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.


            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment)
                    .commit();
        } else {
            Intent movieDetailsIntent = new Intent(this, MovieDetailActivity.class);
            movieDetailsIntent.putExtra("movieExtra", args);

            this.startActivity(movieDetailsIntent);
        }


    }

    @Override
    public void onFinishLoadingData(Movie movie) {

        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.


            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment)
                    .commit();
        }

    }


    @Override
    public void onItemSelected(Trailer trailer) {
        Trailer.watchYoutubeVideo(trailer.getKey(), this);
    }

    public static ArrayList<Movie> getFavorites() {
        ArrayList<Movie> favs = new ArrayList<>();
        SharedPreferences sharedpreferences = MainActivity.context.getSharedPreferences(DetailFragment.FAVORITE_MOVIES, Context.MODE_PRIVATE);
        Map<String, String> favorites = (Map<String, String>) sharedpreferences.getAll();

        for (Map.Entry<String, String> entry : favorites.entrySet()) {

            if (!"sort_pref".equals(entry.getKey())) {
                try {
                    favs.add(new Movie(new JSONObject(entry.getValue())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        return favs;
    }


}
