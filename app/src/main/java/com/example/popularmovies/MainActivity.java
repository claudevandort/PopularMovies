package com.example.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.popularmovies.adapters.MovieAdapter;
import com.example.popularmovies.tasks.TMDB;

public class MainActivity extends AppCompatActivity {
    public MovieAdapter mMovieAdapter;
    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter();

        TMDB.MoviesFetchAsyncTask task = new TMDB.MoviesFetchAsyncTask(this);
        task.execute("popular");
    }
}
