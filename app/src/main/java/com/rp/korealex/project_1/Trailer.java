package com.rp.korealex.project_1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by korealex on 5/15/16.
 */
public class Trailer extends ResponseData implements Parcelable {

    private String id;
    private String name;
    private String size;
    private String key;

    public Trailer(String id, String name, String size, String key) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.key = key;
        this.type = ResponseData.TRAILERS;
    }


    public Trailer(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("size"), jsonObject.getString("key"));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.key);
    }

    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.size = in.readString();
        this.key = in.readString();
        this.type = ResponseData.TRAILERS;

    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public static void watchYoutubeVideo(String id, Context context) {
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
