package com.rp.korealex.project_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private boolean mTwoPane;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);
//        updateList();
//        setupActionBar();

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_details) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
        MovieGridFragment movieGridFragment = ((MovieGridFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_movie_list));

    }



    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setLogo(R.drawable.logo_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        gridView = (GridView) findViewById(R.id.movie_grid_view);
//        adapter = new GridImageAdapter(this, movies);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(adapter);
//
//    }

    @Override
    protected void onResume() {
        super.onResume();


    }














}
