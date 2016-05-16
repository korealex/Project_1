package com.rp.korealex.project_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by korealex on 5/16/16.
 */
public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Review getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View list;
        list = inflater.inflate(R.layout.review_item, null);

        TextView author = (TextView) list.findViewById(R.id.review_name);
        author.setText(reviews.get(position).getAuthor());
        TextView text = (TextView) list.findViewById(R.id.review_text);
        text.setText(reviews.get(position).getContent());
        return list;

    }
}
