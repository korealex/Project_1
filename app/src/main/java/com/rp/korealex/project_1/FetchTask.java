package com.rp.korealex.project_1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by korealex on 5/15/16.
 */
public class FetchTask extends AsyncTask<String, Void, ArrayList<? extends ResponseData>> {

    public Context context;
    public HTTPUtil httpUtil;

    public FetchTask() {

    }

    public FetchTask(Context context, String type) {
        this.context = context;
        httpUtil = new HTTPUtil(type, context);
    }


    public final String LOG_TAG = FetchTask.class.getSimpleName();


    @Override
    public ArrayList<? extends ResponseData> doInBackground(String... params) {


        ArrayList<? extends ResponseData> movies = httpUtil.getHttpData(httpUtil.urlBuilder(params[0], null));

        Log.v(LOG_TAG, "test 1");
        return movies;


    }


}