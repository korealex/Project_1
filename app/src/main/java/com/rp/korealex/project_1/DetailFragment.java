package com.rp.korealex.project_1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by korealex on 5/14/16.
 */
public class DetailFragment extends android.support.v4.app.Fragment {

    public Context context;
    public TrailerFetchTask trailerFetchTask;

    public ArrayList<Trailer> trailers;
    public ArrayList<Review> reviews;
    public Movie movie;
    public TrailerAdapter trailerAdapter;
    public ReviewAdapter reviewAdapter;

    ListView trailerList;
    ListView reviewList;


    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    public static final String FAVORITE_MOVIES = "favorite_movies";
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_DATA = "movie_data";

    @Override
    public void onCreate(Bundle savedInstanceState) {


        context = getActivity();
        sharedpreferences = context.getSharedPreferences(FAVORITE_MOVIES, Context.MODE_APPEND);
        trailerFetchTask = new TrailerFetchTask(context);

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Movie> favs = MainActivity.getFavorites();
        boolean is_fav = false;


        View rootView = inflater.inflate(R.layout.movie_details, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {

//             fragmentManager = getFragmentManager();
//             fragmentTransaction = fragmentManager.beginTransaction();

            movie = arguments.getParcelable("movie");

            for (Movie mov : favs) {
                if (mov.id == movie.id) {
                    is_fav = true;

                }

            }


            trailerFetchTask.execute("videos", Integer.toString(movie.id));


            TextView tv;
            tv = (TextView) rootView.findViewById(R.id.movie_title);
            tv.setText(movie.title);

            tv = (TextView) rootView.findViewById(R.id.year);
            tv.setText(movie.getReleaseYear());

            tv = (TextView) rootView.findViewById(R.id.description);
            tv.setText(movie.overview);

            tv = (TextView) rootView.findViewById(R.id.rating);
            tv.setText(movie.vote_average + "/10");

            trailerList = (ListView) rootView.findViewById(R.id.trailer_list_view);


            ImageView imageView = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load(getActivity().getString(R.string.url_poster_img_185_res) + movie.poster_path).into(imageView);

            final Button favorites_btn = (Button) rootView.findViewById(R.id.button_favorites);


            editor = sharedpreferences.edit();

            if (is_fav) {

                favorites_btn.setText("Favorite");
                favorites_btn.setBackgroundColor(Color.CYAN);


                favorites_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.remove(movie.id + "");

                        editor.commit();
                        Toast.makeText(context, "Removed from favorites.", Toast.LENGTH_LONG).show();
                        favorites_btn.setText("Mark as Favorite");
                        favorites_btn.setBackgroundColor(Color.CYAN);


                    }
                });


            } else {

                favorites_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editor.putString(movie.id + "", movie.jsonString);

                        editor.commit();
                        Toast.makeText(context, "Saved to favorites.", Toast.LENGTH_LONG).show();
                        favorites_btn.setText("Favorite");


                    }
                });

            }


            Button review_btn = (Button) rootView.findViewById(R.id.reviews_btn);


            review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent movieDetailsIntent = new Intent(context, ReviewsActivity.class);
                    movieDetailsIntent.putExtra("id", Integer.toString(movie.id));

                    context.startActivity(movieDetailsIntent);
                }
            });


        }

        return rootView;
    }

    public void setTrailers(ArrayList<? extends ResponseData> trailers) {
        this.trailers = (ArrayList<Trailer>) trailers;
        trailerAdapter = new TrailerAdapter(context, this.trailers);
        trailerList.setAdapter(trailerAdapter);
        trailerList.setOnItemClickListener(trailerAdapter);


    }


    public class TrailerFetchTask extends FetchTask {
        public TrailerFetchTask(Context context) {
            super(context, ResponseData.TRAILERS);
        }

        @Override
        protected void onPostExecute(ArrayList<? extends ResponseData> trailers) {
            setTrailers(trailers);
        }

        @Override
        public ArrayList<? extends ResponseData> doInBackground(String... params) {

            ArrayList<? extends ResponseData> trailers = httpUtil.getHttpData(httpUtil.urlBuilder(params[0], params[1]));

            Log.v(LOG_TAG, "test 1");
            return trailers;
        }
    }


}
