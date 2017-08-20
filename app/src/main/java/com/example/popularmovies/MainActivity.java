package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.adapters.MovieAdapter;
import com.example.popularmovies.tasks.TMDB;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{
    private final String TAG = this.getClass().getSimpleName();
    public MovieAdapter mMovieAdapter;
    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);

        TMDB.MoviesFetchAsyncTask task = new TMDB.MoviesFetchAsyncTask(this);
        task.execute(TMDB.POPULAR_PARAM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TMDB.MoviesFetchAsyncTask task = new TMDB.MoviesFetchAsyncTask(this);
        switch (item.getItemId()){
            case R.id.action_popular: task.execute(TMDB.POPULAR_PARAM);
                break;
            case R.id.action_top_rated: task.execute(TMDB.TOP_RATED_PARAM);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ITEM_INDEX", clickedItemIndex);
        startActivity(intent);
    }
}
