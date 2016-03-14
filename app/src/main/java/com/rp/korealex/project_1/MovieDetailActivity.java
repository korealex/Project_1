package com.rp.korealex.project_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by korealex on 3/13/16.
 */
public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);


       String jsonMovieString = getIntent().getStringExtra("movie");
        try {
            JSONObject movieJson = new JSONObject(jsonMovieString);
            Movie movie = new Movie(movieJson);

            TextView tv;
            tv = (TextView) findViewById(R.id.movie_title);
            tv.setText(movie.title);

            tv = (TextView) findViewById(R.id.year);
            tv.setText(movie.getReleaseYear());

            tv = (TextView) findViewById(R.id.description);
            tv.setText(movie.overview);

            tv = (TextView) findViewById(R.id.rating);
            tv.setText(Double.toString(movie.vote_average )+ "/10");

            ImageView imageView = (ImageView)  findViewById(R.id.poster);
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185"+movie.poster_path).into(imageView);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
