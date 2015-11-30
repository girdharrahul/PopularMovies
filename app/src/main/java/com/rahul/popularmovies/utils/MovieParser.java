package com.rahul.popularmovies.utils;

import android.util.Log;

import com.rahul.popularmovies.bean.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rahul_000 on 02-11-2015.
 */
public class MovieParser {

   public static  final String LOG_TAG=MovieParser.class.getSimpleName();


    public static ArrayList<Movie> getMoviesDataFromJson(String moviesJsonStr)

    {

        ArrayList<Movie> moviesArrayList=new ArrayList<>();

        final String RESULT_LIST = "results";
        final String TITLE = "original_title";
        final String POSTER = "poster_path";
        final String SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String POSTER_BASE="http://image.tmdb.org/t/p/w185/";

        Movie mMovie;

        try {
            JSONObject moviesJson = new JSONObject(moviesJsonStr);


            JSONArray resultsArray = moviesJson.getJSONArray(RESULT_LIST);


            for(int i=0;i<resultsArray.length();i++)
            {

                JSONObject movieObject=resultsArray.getJSONObject(i);
                mMovie=new Movie();
                mMovie.setTitle(movieObject.getString(TITLE));
                mMovie.setPoster(POSTER_BASE + movieObject.getString(POSTER));
                mMovie.setSynopsis(movieObject.getString(SYNOPSIS));
                mMovie.setRating(movieObject.getString(USER_RATING));
                mMovie.setReleaseDate(movieObject.getString(RELEASE_DATE));


                moviesArrayList.add(mMovie);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(Movie m:moviesArrayList)
        {
            Log.d(LOG_TAG,m.getTitle()+" "+ m.getPoster()+" "+ m.getRating()+" "+ m.getReleaseDate()+" "+ m.getSynopsis()+" "+" "+m.getRating());
        }


        return moviesArrayList;
    }

}
