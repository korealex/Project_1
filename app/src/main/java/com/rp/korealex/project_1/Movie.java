package com.rp.korealex.project_1;

/**
 * Created by korealex on 3/12/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * "adult": false,
 * "backdrop_path": "/W0MNr3XN95U5KLD9xIQD96YKcS.jpg",
 * "genre_ids": [
 * 878,
 * 35,
 * 28,
 * 14
 * ],
 * "id": 251516,
 * "original_language": "en",
 * "original_title": "Kung Fury",
 * "overview": "During an unfortunate series of events, a friend of Kung Fury is assassinated by the most dangerous kung fu master criminal of all time, Adolf Hitler, a.k.a Kung Führer. Kung Fury decides to travel back in time to Nazi Germany in order to kill Hitler and end the Nazi empire once and for all.",
 * "release_date": "2015-05-28",
 * "poster_path": "/oJWzpGCLIj3uYa0ux19T2WwzTOf.jpg",
 * "popularity": 4.224303,
 * "title": "Kung Fury",
 * "video": false,
 * "vote_average": 8.5,
 * "vote_count": 64
 */
public class Movie implements Parcelable {
    public boolean adult;
    public String backdrop_path;
    public JSONArray genre_ids;
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public String release_date;
    public String poster_path;
    public double popularity;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public String jsonString;


    public Movie(boolean adult, String backdrop_path, JSONArray genre_ids, int id, String original_language, String original_title, String overview, String release_date, String poster_path, double popularity, String title, boolean video, double vote_average, int vote_count, String jsonString) {

        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.jsonString = jsonString;
    }


    public Movie(JSONObject jsonObject) throws JSONException {
        //
        this(jsonObject.getBoolean("adult"),
                jsonObject.getString("backdrop_path"),
                jsonObject.getJSONArray("genre_ids"),
                jsonObject.getInt("id"),
                jsonObject.getString("original_language"),
                jsonObject.getString("original_title"),
                jsonObject.getString("overview"),
                jsonObject.getString("release_date"),
                jsonObject.getString("poster_path"),
                jsonObject.getDouble("popularity"),
                jsonObject.getString("title"),
                jsonObject.getBoolean("video"),
                jsonObject.getDouble("vote_average"),
                jsonObject.getInt("vote_count"),
                jsonObject.toString()
        );


    }


    public String toJsonString() {
        return  this.jsonString;

    }

    public String getReleaseYear(){
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        try {
            Date date = year.parse(this.release_date);
            return "" + year.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.genre_ids.toString());
        dest.writeInt(this.id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
        dest.writeString(this.jsonString);
    }

    protected Movie(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.genre_ids = in.readParcelable(JSONArray.class.getClassLoader());
        this.id = in.readInt();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
        this.jsonString = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
