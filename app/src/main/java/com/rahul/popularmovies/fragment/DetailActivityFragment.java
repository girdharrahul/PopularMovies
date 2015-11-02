package com.rahul.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    ImageView poster;
    TextView title;
    TextView synopsis;
    TextView rating;
    TextView release_date;


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        poster = (ImageView) rootView.findViewById(R.id.detail_poster_imageview);
        title = (TextView) rootView.findViewById(R.id.detail_title_textview);
        synopsis = (TextView) rootView.findViewById(R.id.detail_synopsis_textview);

        rating = (TextView) rootView.findViewById(R.id.detail_rating_textview);

        release_date = (TextView) rootView.findViewById(R.id.detail_releasedate_textview);

        title.setText(getActivity().getIntent().getStringExtra("title"));
        synopsis.setText(getActivity().getIntent().getStringExtra("synopsis"));
        rating.setText(getActivity().getIntent().getStringExtra("rating"));
        release_date.setText(getActivity().getIntent().getStringExtra("release_date"));
        Picasso.with(getContext()).load(getActivity().getIntent().getStringExtra("poster")).into(poster);


        return rootView;
    }
}
