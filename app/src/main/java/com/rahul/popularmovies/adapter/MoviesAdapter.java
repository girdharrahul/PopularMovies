package com.rahul.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.rahul.popularmovies.R;
import com.rahul.popularmovies.activity.DetailActivity;
import com.rahul.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rahul_000 on 31-10-2015.
 */
public class MoviesAdapter extends ArrayAdapter<Movie>{

    private ArrayList<Movie> mMovieList;
    Context mContext;

    private static final String LOG_TAG=MoviesAdapter.class.getSimpleName();




    public MoviesAdapter(Context context,ArrayList<Movie> moviesList)
    {

        super(context,0,moviesList);
        mContext=context;
        mMovieList=moviesList;


    }

    class ViewHolder {
        ImageView poster;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ViewHolder holder;


        if(convertView==null)
        {
            convertView= inflater.inflate(R.layout.grid_item_movie, parent, false);
            holder=new ViewHolder();
            holder.poster=(ImageView)convertView.findViewById(R.id.grid_item_poster);
            convertView.setTag(holder);

        }

        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        Movie movie=getItem(position);


        Log.d(LOG_TAG, movie.getPoster());

        Picasso.with(getContext()).load(movie.getPoster()).into(holder.poster);



        final String title=movie.getTitle();
        final String synopsis=movie.getSynopsis();
        final String rating=movie.getRating();
        final String release_date = movie.getReleaseDate();
        final String poster_url = movie.getPoster();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, DetailActivity.class);
                i.putExtra("poster", poster_url);
                i.putExtra("title", title);
                i.putExtra("synopsis", synopsis);
                i.putExtra("rating", rating);
                i.putExtra("release_date",release_date);
                mContext.startActivity(i);

            }
        });


        return  convertView;


    }



}
