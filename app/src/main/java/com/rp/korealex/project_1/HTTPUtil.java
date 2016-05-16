package com.rp.korealex.project_1;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

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

/**
 * Created by korealex on 5/15/16.
 */
public class HTTPUtil {

    public final String LOG_TAG = getClass().getSimpleName();

    public Resources res;

    public RequestHandler requestHandler;
    public String type;

    public HTTPUtil(String type, Context context) {
        this.type = type;
        this.res = context.getResources();
    }


    public String urlBuilder(String path, String resource) {

        String api_key = BuildConfig.IMDB_API_KEY;

        final String PAGE_PARAM = "page";
        final String APP_KEY_PARAM = "api_key";

        String movies_base_url = "";


        Uri buildUri;

        switch (type) {

            case ResponseData.MOVIES:
                movies_base_url = res.getString(R.string.api_url) + path;
                break;

            case ResponseData.REVIEWS:
            case ResponseData.TRAILERS:
                movies_base_url = res.getString(R.string.api_url) + resource + "/" + path;
                break;


            default:

                break;

        }


        buildUri = Uri.parse(movies_base_url).buildUpon()
                .appendQueryParameter(APP_KEY_PARAM, api_key)
                .build();


        return buildUri.toString();


    }


    public ArrayList<? extends ResponseData> getHttpData(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultsJsonString = null;


        try {


            URL url = new URL(urlString);
            Log.v("url", urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;

            }
            resultsJsonString = buffer.toString();
            Log.v(" response ", resultsJsonString);


            return getDataFromJson(resultsJsonString);

        } catch (IOException e) {

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


    public ArrayList<? extends ResponseData> getDataFromJson(String responseJsonString) {

        final String OWM_LIST = "results";

        try {

            JSONObject responseJson = new JSONObject(responseJsonString);

            if (this.type == ResponseData.MOVIES) {

                requestHandler = new RequestHandler(
                        Integer.parseInt(responseJson.getString("page")),
                        Integer.parseInt(responseJson.getString("total_pages")),
                        Integer.parseInt(responseJson.getString("total_results"))

                );
            } else {
                requestHandler = new RequestHandler();

            }


            JSONArray responseArray = responseJson.getJSONArray(OWM_LIST);


            switch (this.type) {

                case ResponseData.MOVIES:

                    for (int i = 0; i < responseArray.length(); i++) {
                        requestHandler.addResponseData(new Movie(responseArray.getJSONObject(i)));
                    }


                    break;

                case ResponseData.REVIEWS:

                    for (int i = 0; i < responseArray.length(); i++) {
                        requestHandler.addResponseData(new Review(responseArray.getJSONObject(i)));
                    }

                    break;

                case ResponseData.TRAILERS:

                    for (int i = 0; i < responseArray.length(); i++) {
                        requestHandler.addResponseData(new Trailer(responseArray.getJSONObject(i)));
                    }


                    break;


                default:

                    break;

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return requestHandler.responseData;

    }


}
