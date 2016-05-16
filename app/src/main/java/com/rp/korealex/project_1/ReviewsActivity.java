package com.rp.korealex.project_1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by korealex on 5/16/16.
 */
public class ReviewsActivity extends AppCompatActivity {
    public ReviewAdapter reviewAdapter;
    ListView reviewList;
    public ArrayList<Review> reviews;
    public ReviewFetchTask reviewFetchTask;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        setContentView(R.layout.reviews_detail);
        reviewFetchTask = new ReviewFetchTask(this);

        reviewList = (ListView) findViewById(R.id.reviews_list_view);

        String id = getIntent().getStringExtra("id");

        reviewFetchTask.execute("reviews", id);


    }

    public void setReviews(ArrayList<? extends ResponseData> reviews) {
        this.reviews = (ArrayList<Review>) reviews;

        reviewAdapter = new ReviewAdapter(this, this.reviews);
        reviewList.setAdapter(reviewAdapter);

    }

    public class ReviewFetchTask extends FetchTask {
        public ReviewFetchTask(Context context) {
            super(context, ResponseData.REVIEWS);
        }

        @Override
        protected void onPostExecute(ArrayList<? extends ResponseData> reviews) {
            setReviews(reviews);
        }

        @Override
        public ArrayList<? extends ResponseData> doInBackground(String... params) {

            ArrayList<? extends ResponseData> trailers = httpUtil.getHttpData(httpUtil.urlBuilder(params[0], params[1]));

            Log.v(LOG_TAG, "test 1");
            return trailers;
        }
    }

}
