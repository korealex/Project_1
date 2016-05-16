package com.rp.korealex.project_1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by korealex on 5/15/16.
 */
public class TrailerAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<Trailer> trailers = new ArrayList<>();


    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Trailer getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((TCallback) context).onItemSelected(getItem(position));
    }


    public interface TCallback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Trailer trailer);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View list;

        list = inflater.inflate(R.layout.trailer_item, null);


        TextView textView = (TextView) list.findViewById(R.id.trailer_name);
        textView.setText(trailers.get(position).getName());


        return list;


    }


    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            context.startActivity(intent);
        }
    }
}
