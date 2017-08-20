package com.example.popularmovies.tasks;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.MainActivity;
import com.example.popularmovies.R;
import com.example.popularmovies.adapters.MovieAdapter;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by claudioguerra on 8/19/17.
 */

public class TMDB {
    private static String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static String API_KEY = "api_key";
    private static String API_KEY_PARAM = BuildConfig.TMDB_API_KEY;
    private static ArrayList<Movie> mMovies = new ArrayList<>();

    public static ArrayList<Movie> getMovies(){
        return mMovies;
    }

    public static class MoviesFetchAsyncTask extends AsyncTask<String, Void, String>{
        private final List<String> PERMITTED_PARAMS = Arrays.asList("popular", "top_rated");
        private Context mContext;
        private ProgressBar mProgressBar;

        public MoviesFetchAsyncTask(Context context){
            mContext = context;
            mProgressBar = (ProgressBar) ((MainActivity) mContext).findViewById(R.id.main_progress_bar);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String sortParam = params[0];
            String stringResult = null;
            if(PERMITTED_PARAMS.contains(sortParam)) {
                stringResult = getStringResult(sortParam);
                if(stringResult != null && !stringResult.equals("")){
                    mMovies = new ArrayList<>();
                    parseAndSave(stringResult);
                }
            }
            return stringResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MovieAdapter adapter = ((MainActivity) mContext).mMovieAdapter;
            RecyclerView recyclerView = ((MainActivity) mContext).mRecyclerView;
            recyclerView.setAdapter(adapter);

            mProgressBar.setVisibility(View.GONE);
            if(mMovies.size() == 0)
                Toast.makeText(mContext, "Couldn't get any movie :(", Toast.LENGTH_LONG).show();
        }

        private void parseAndSave(String stringResult){
            try {
                JSONObject jsonResult = new JSONObject(stringResult);
                JSONArray jsonMovies = jsonResult.getJSONArray("results");
                for(int i = 0; i < jsonMovies.length(); i++) {
                    JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                    long id = jsonMovie.getLong("id");
                    String originalTitle = jsonMovie.getString("original_title");
                    String posterPath = jsonMovie.getString("poster_path");
                    String overview = jsonMovie.getString("overview");
                    float voteAverage = (float) jsonMovie.getDouble("vote_average");
                    String releaseDate = jsonMovie.getString("release_date");
                    Movie movie = new Movie(id, originalTitle, posterPath, overview, voteAverage, releaseDate);
                    mMovies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String getStringResult(String sortParam){
            String stringResult = null;
            try{
                stringResult = NetworkUtils.getResponseFromHttpUrl(buildMoviesUrl(sortParam));
            }catch (IOException e){
                e.printStackTrace();
            }
            return stringResult;
        }

        private Uri buildMoviesUri(String sortParam){
            return Uri.parse(BASE_URL + "/" + sortParam).buildUpon()
                    .appendQueryParameter(API_KEY, API_KEY_PARAM)
                    .build();
        }

        private URL buildMoviesUrl(String sortParam){
            URL url = null;
            try{
                url = new URL(buildMoviesUri(sortParam).toString());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            return url;
        }
    }
}
