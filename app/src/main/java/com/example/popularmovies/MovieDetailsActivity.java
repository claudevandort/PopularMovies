package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.models.Movie;
import com.example.popularmovies.tasks.TMDB;
import com.squareup.picasso.Picasso;

/**
 * Created by claudioguerra on 8/20/17.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mYearTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;
    private Movie mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitleTextView = (TextView) findViewById(R.id.movie_title_text_view);
        mPosterImageView = (ImageView) findViewById(R.id.movie_poster_image_view);
        mYearTextView = (TextView) findViewById(R.id.movie_detail_year_text_view);
        mVoteAverageTextView = (TextView) findViewById(R.id.movie_vote_average_text_view);
        mOverviewTextView = (TextView) findViewById(R.id.movie_overview_text_view);

        Intent intent = getIntent();
        if(intent.hasExtra("MOVIE_ITEM_INDEX")){
            int movieItemIndex = intent.getIntExtra("MOVIE_ITEM_INDEX", 0);
            mMovie = TMDB.getMovies().get(movieItemIndex);
            fillViews();
        }
    }

    private void fillViews(){
        mTitleTextView.setText(mMovie.getOriginalTitle());
        Picasso.with(this)
                .load(TMDB.IMAGE_BASE_URL + mMovie.getPosterPath())
                .placeholder(R.drawable.ic_movie_creation_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(mPosterImageView);
        mYearTextView.setText(String.valueOf(1900 + mMovie.getReleaseDate().getYear()));
        mVoteAverageTextView.setText(String.format("%.1f/10", mMovie.getVoteAverage()));
        mOverviewTextView.setText(mMovie.getOverview());
    }
}
