package com.rahul.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rahul.popularmovies.R;
import com.rahul.popularmovies.fragment.DetailActivityFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailActivityFragment()).commit();
        }



    }

}
