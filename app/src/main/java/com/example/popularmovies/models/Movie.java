package com.example.popularmovies.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by claudioguerra on 8/20/17.
 */

public class Movie {
    private long mId;
    private String mOriginalTitle;
    private String mPosterPath;
    private final List<String> PERMITTED_POSTER_SIZES = Arrays.asList("original", "w92", "w154", "w185", "w342", "w500", "w780");
    private String mOverview;
    private float mVoteAverage;
    private Date mReleaseDate;

    // Constructor with string date yyyy-MM-dd
    public Movie(long id, String originalTitle, String posterPath, String overview, float voteAverage, String releaseDateString){
        this(id, originalTitle, posterPath, overview, voteAverage);
        Date releaseDate = null;
        try {
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mReleaseDate = releaseDate;
    }

    // Constructor without date
    public Movie(long id, String originalTitle, String posterPath, String overview, float voteAverage){
        mId = id;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
    }

    // Standard constructor
    public Movie(long id, String originalTitle, String posterPath, String overview, float voteAverage, Date releaseDate){
        mId = id;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    // GETTERS
    public long getId(){ return mId; }

    public String getOriginalTitle(){ return mOriginalTitle; }

    public String getPosterPath(){ return getPosterPath("w185"); }

    public String getPosterPath(String size){
        if(PERMITTED_POSTER_SIZES.contains(size))
            return size + mPosterPath;
        else
            return null;
    }

    public String getOverview(){ return mOverview; }

    public float getVoteAverage(){ return mVoteAverage; }

    public Date getReleaseDate(){ return mReleaseDate; }
}
