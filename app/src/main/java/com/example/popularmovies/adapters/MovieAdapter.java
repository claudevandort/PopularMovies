package com.example.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.tasks.TMDB;
import com.squareup.picasso.Picasso;

/**
 * Created by claudioguerra on 8/20/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private final ListItemClickListener mListItemClickListener;

    public MovieAdapter(ListItemClickListener listItemClickListener){
        mListItemClickListener = listItemClickListener;
    }

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

    public interface ListItemClickListener{
        void onListItemClickListener(int clickedItemIndex);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mMoviePosterImageView;
        Context mContext;

        public MovieViewHolder(View itemView) {
            super(itemView);

            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_poster_image_view);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            Movie movie = TMDB.getMovies().get(listIndex);

            String posterUrl = TMDB.IMAGE_BASE_URL + movie.getPosterPath();
            Picasso.with(mContext)
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_movie_creation_black_24dp)
                    .error(R.drawable.ic_error_outline_black_24dp)
                    .into(mMoviePosterImageView);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onListItemClickListener(getAdapterPosition());
        }
    }
}
