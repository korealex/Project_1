package com.rp.korealex.project_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by korealex on 5/14/16.
 */
public class MovieGridFragment extends Fragment {

    public final String LOG_TAG = getClass().getSimpleName();


    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    GridView gridView;

    private RequestHandler requestHandler;
    private GridImageAdapter adapter;
    public ArrayList<Movie> movies = new ArrayList<>();
    protected String jsonResponse;
    String sort;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public interface Callback {

        void onFinishLoadingData(Movie movie);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        sharedpreferences = getContext().getSharedPreferences(DetailFragment.FAVORITE_MOVIES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        updateList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new GridImageAdapter(getActivity(), movies);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
        if (id == R.id.action_top_rated) {

            changeSortPref(getString(R.string.sort_value_top_rated));
            updateList();

            return true;
        }
        if (id == R.id.action_popular) {

            changeSortPref(getString(R.string.sort_value_popular));
            updateList();

            return true;
        }
        if (id == R.id.action_favorites) {

            try {
                showFavorites();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        if (id == R.id.action_settings) {

            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);

            return true;
        }

//        if(id == R.id.action_refresh){
//
//            getMovies(sort,"1");
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);

    }


    public boolean checkConnectivity() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

    public void updateList() {

        Log.v(" updateList ", "test Final");
        sort = sharedpreferences.getString(getString(R.string.sort_pref_key), getString(R.string.sort_value_default));

        getMovies(sort, "1");


    }

    public void changeSortPref(String pref) {

        editor.putString(getString(R.string.sort_pref_key), pref);
        editor.commit();

    }

    public void getMovies(String category, String page) {


        if (checkConnectivity()) {
            FetchMovieTask moviesTask = new FetchMovieTask(getActivity());
            moviesTask.execute(category, page);
        } else {

            Toast.makeText(getActivity(), R.string.lost_connectivity_warning, Toast.LENGTH_SHORT).show();
        }

    }

    public void showFavorites() throws JSONException {

        ArrayList<Movie> favs = MainActivity.getFavorites();


        if (!favs.isEmpty()) {
            updateGrid(favs);

        } else {
            Toast.makeText(getActivity(), "There are no favorite movies saved.", Toast.LENGTH_SHORT).show();

        }
    }


    public void updateGrid(ArrayList<Movie> moviesArray) {


        if (!adapter.isEmpty()) {
            adapter.clear();

        }


        Log.v(" updateGrid ", "" + moviesArray.size());

        adapter = new GridImageAdapter(getActivity(), moviesArray);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);

        ((Callback) getActivity()).onFinishLoadingData(moviesArray.get(0));
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;

    }
    ///////////////////////////////////////////
    ///////////////////////////////////////////


    public class FetchMovieTask extends FetchTask {
        public FetchMovieTask(Context context) {
            super(context, ResponseData.MOVIES);
        }

        @Override
        protected void onPostExecute(ArrayList<? extends ResponseData> movies) {
            updateGrid((ArrayList<Movie>) movies);
        }


    }
}
