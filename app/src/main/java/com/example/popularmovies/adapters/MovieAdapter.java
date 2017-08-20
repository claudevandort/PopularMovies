package com.example.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.tasks.TMDB;

/**
 * Created by claudioguerra on 8/20/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return TMDB.getMovies().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView mMovieNameTextView;


        public MovieViewHolder(View itemView) {
            super(itemView);

            mMovieNameTextView = (TextView) itemView.findViewById(R.id.movie_title);
        }

        void bind(int listIndex){
            mMovieNameTextView.setText(TMDB.getMovies().get(listIndex).getName());
        }
    }
}
