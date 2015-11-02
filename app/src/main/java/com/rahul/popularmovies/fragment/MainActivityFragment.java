package com.rahul.popularmovies.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rahul.popularmovies.R;
import com.rahul.popularmovies.adapter.MoviesAdapter;
import com.rahul.popularmovies.bean.Movie;
import com.rahul.popularmovies.utils.MovieParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView movies_grid;
    MoviesAdapter mMoviesAdapter;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FetchMoviesTask();



    }



    public void FetchMoviesTask()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String criteria=prefs.getString(getString(R.string.pref_sortorder_key),getString(R.string.pref_sortorder_default));

        FetchMovies mFetchMovies = new FetchMovies();
        mFetchMovies.execute(criteria);
    }


    @Override
    public void onStart() {
        super.onStart();

        FetchMoviesTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movies_grid = (GridView) rootView.findViewById(R.id.movies_grid);



        return rootView;
    }


    public class FetchMovies extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.


            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            String sort_by = params[0];

                    //Generate the key from moviedb.org and paste it here
            String api_key = "XXXXXXXXXXXXXXXXXXXXXXXX";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sort_by)
                    .appendQueryParameter(API_KEY_PARAM, api_key)
                    .build();

            Log.d(LOG_TAG, builtUri.toString());
            String moviesJsonStr;


            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            // Create the request to OpenWeatherMap, and open the connection
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            }
            try {
                urlConnection.connect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // Read the input stream into a String
            InputStream inputStream = null;
            try {
                inputStream = urlConnection.getInputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();

            Log.d(LOG_TAG, "Moveis Json String " + moviesJsonStr);


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


            ArrayList<Movie> moviesList = MovieParser.getMoviesDataFromJson(moviesJsonStr);

            return moviesList;


        }


        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList) {

            super.onPostExecute(moviesList);

            mMoviesAdapter = new MoviesAdapter(getActivity(), moviesList);

            movies_grid.setAdapter(mMoviesAdapter);




        }


    }


}





