package com.example.popularmovies.tasks;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.R;
import com.example.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by claudioguerra on 8/19/17.
 */

public class TMDB {
    private static String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static String API_KEY = "api_key";
    private static String API_KEY_PARAM = BuildConfig.TMDB_API_KEY;

    public static class MoviesFetchAsyncTask extends AsyncTask<String, Void, String>{
        private List<String> allowedParams = Arrays.asList("popular", "top_rated");
        private Context mContext;

        public MoviesFetchAsyncTask(Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String sortParam = params[0];
            String stringResult = null;
            if(allowedParams.contains(sortParam))
                stringResult = getStringResult(sortParam);
            return stringResult;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = (TextView) ((Activity) mContext).findViewById(R.id.main_text_view);
            textView.setText(s);
        }

        public String getStringResult(String sortParam){
            String stringResult = null;
            try{
                stringResult = NetworkUtils.getResponseFromHttpUrl(buildMoviesUrl(sortParam));
            }catch (IOException e){
                e.printStackTrace();
            }
            return stringResult;
        }

        public Uri buildMoviesUri(String sortParam){
            return Uri.parse(BASE_URL + "/" + sortParam).buildUpon()
                    .appendQueryParameter(API_KEY, API_KEY_PARAM)
                    .build();
        }

        public URL buildMoviesUrl(String sortParam){
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
