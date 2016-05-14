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
    public final String LOG_TAG = MainActivity.class.getSimpleName();

    GridView gridView;
    private RequestHandler requestHandler ;
    private GridImageAdapter adapter;
    public ArrayList<Movie> movies = new ArrayList<>();
    protected String jsonResponse;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String sort;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
        if(id == R.id.action_top_rated){

            changeSortPref(getString(R.string.sort_value_top_rated));
            updateList();

            return true;
        }
        if(id == R.id.action_popular){

            changeSortPref(getString(R.string.sort_value_popular));
            updateList();

            return true;
        }

        if(id == R.id.action_settings){

            Intent intent = new Intent(this, SettingsActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        setContentView(R.layout.activity_main);
        updateList();
        setupActionBar();

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
    protected void onStart() {
        super.onStart();

        gridView = (GridView) findViewById(R.id.movie_grid_view);
        adapter = new GridImageAdapter(this, movies);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();

    }

    public void updateList(){

        sort = preferences.getString(getString(R.string.sort_pref_key),  getString(R.string.sort_value_default));
        getMovies(sort,"1");


    }
    public void changeSortPref(String pref){

        editor.putString(getString(R.string.sort_pref_key), pref);
        editor.commit();

    }

    public void getMovies(String category, String page) {
        if (checkConnectivity()) {
            FetchMovieTask moviesTask = new FetchMovieTask();
            moviesTask.execute(category, page);
        } else {

            Toast.makeText(this, R.string.lost_connectivity_warning, Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkConnectivity(){

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }


    public void updateGrid(ArrayList<Movie> moviesArray){

        if(!adapter.isEmpty()){
            adapter.clear();

        }
        adapter = new GridImageAdapter(this, moviesArray);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);
    }




    public class FetchMovieTask extends AsyncTask<String,Void, ArrayList<Movie>>{

        public final String LOG_TAG = FetchMovieTask.class.getSimpleName();

//        public FetchMovieTask(){
//
//
//        }


        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            //super.onPostExecute(movies);

            updateGrid(movies);


        }





        @Override
        public ArrayList<Movie> doInBackground(String... params) {

            ArrayList<Movie> movies = getHttpData(params[0], Integer.parseInt(params[1]) );

            return movies;




        }

        public ArrayList<Movie> getHttpData(String category,int page ){
            HttpURLConnection urlConnection = null;
            BufferedReader reader =  null;
            String moviesJsonString = null;

            String api_key = BuildConfig.IMDB_API_KEY;

            try {

                final String MOVIES_BASE_URL = getString(R.string.api_url)+ category;
                final String PAGE_PARAM = "page";
                final String APP_KEY_PARAM = "api_key";
                Uri buildUri;
                if(page != 0){
                    buildUri =  Uri.parse(MOVIES_BASE_URL).buildUpon()
                            .appendQueryParameter(PAGE_PARAM, "" + page)
                            .appendQueryParameter(APP_KEY_PARAM, api_key)
                            .build();

                }else{
                    buildUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                            .appendQueryParameter(APP_KEY_PARAM, api_key)
                            .build();
                }

                URL url = new URL(buildUri.toString());
                Log.v(LOG_TAG, buildUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }
                reader = new BufferedReader( new InputStreamReader(inputStream));
                String line ;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer.length()==0){
                    return null;

                }
                moviesJsonString = buffer.toString();

//                Log.v(LOG_TAG, moviesJsonString);

                return getMovieDataFromJson(moviesJsonString);

            }catch (IOException e){

                Log.e(LOG_TAG, "Error", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }


        }
        public ArrayList<Movie> getMovieDataFromJson(String moviesJsonString){

            final String OWM_LIST = "results";

            try {

                JSONObject moviesJson = new JSONObject(moviesJsonString);
                requestHandler = new RequestHandler(
                        Integer.parseInt(moviesJson.getString("page")),
                        Integer.parseInt(moviesJson.getString("total_pages")),
                        Integer.parseInt(moviesJson.getString("total_results"))

                );
                JSONArray moviesArray = moviesJson.getJSONArray(OWM_LIST);

                for(int i=0 ; i < moviesArray.length(); i++){
                    requestHandler.addMovie(new Movie(moviesArray.getJSONObject(i)));
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }



            return requestHandler.movies ;

        }




    }

}
