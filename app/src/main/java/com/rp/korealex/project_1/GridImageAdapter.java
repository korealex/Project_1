package com.rp.korealex.project_1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by korealex on 3/13/16.
 */
public class GridImageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();


    public GridImageAdapter(Context context, ArrayList<Movie> movies) {

        Log.v("GridImageAdapter", context.toString());
        Log.v("GridImageAdapter", " " + movies.size());
        this.context = context;
        this.movies = movies;
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Movie movie);
    }


    @Override
    public boolean isEmpty() {
        return movies.isEmpty() || movies == null;

    }

    public void clear() {
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

        ArrayList<Movie> favs = MainActivity.getFavorites();
        boolean is_fav = false;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        gridView = new View(context);

        // get layout from mobile.xml
        gridView = inflater.inflate(R.layout.movie_grid_item, null);


        for (Movie mov : favs) {
            if (mov.id == movies.get(position).id) {
                is_fav = true;

            }

        }

        // set value into textview
//            TextView textView = (TextView) gridView.findViewById(R.id.movie_title);
//            textView.setText(movies.get(position).title);

        // set image based on selected text
        ImageView imageView = (ImageView) gridView.findViewById(R.id.movie_image_grid_view);
        ImageView fav_img = (ImageView) gridView.findViewById(R.id.fav_image);
        if (!is_fav) {
            fav_img.setImageResource(0);


        }
        Picasso.with(context).load(context.getString(R.string.url_poster_img_185_res) + movies.get(position).poster_path).into(imageView);


        return gridView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((Callback) context).onItemSelected(getItem(position));


    }
}
