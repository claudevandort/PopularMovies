package com.example.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.popularmovies.tasks.TMDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TMDB.MoviesFetchAsyncTask task = new TMDB.MoviesFetchAsyncTask(this);
        task.execute("popular");
    }
}
