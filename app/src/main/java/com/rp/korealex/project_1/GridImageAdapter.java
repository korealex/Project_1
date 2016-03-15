package com.rp.korealex.project_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by korealex on 3/13/16.
 */
public class GridImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();


    public GridImageAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public boolean isEmpty() {
        if(movies.isEmpty() || movies == null){
            return true;
        }else{
            return false;
        }

    }

    public void clear(){
        this.movies = null;

    }
    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.movie_grid_item, null);

            // set value into textview
//            TextView textView = (TextView) gridView.findViewById(R.id.movie_title);
//            textView.setText(movies.get(position).title);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.movie_image_grid_view);
            Picasso.with(context).load(context.getString(R.string.url_poster_img_185_res)+movies.get(position).poster_path).into(imageView);






        return gridView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent movieDetails = new Intent(context, MovieDetailActivity.class);
        movieDetails.putExtra("movie",getItem(position).toJsonString());

        context.startActivity(movieDetails);
        //detailForecast.setData(Uri.parse(fileUrl));

        //startActivity(detailForecast);


    }
}
