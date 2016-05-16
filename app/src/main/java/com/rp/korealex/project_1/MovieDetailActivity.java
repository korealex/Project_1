package com.rp.korealex.project_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by korealex on 3/13/16.
 */
public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = getIntent().getBundleExtra("movieExtra");
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment).commit();
        }


    }

    @Override
    public void onItemSelected(Trailer trailer) {

        Trailer.watchYoutubeVideo(trailer.getKey(), this);


    }


}
