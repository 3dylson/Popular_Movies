package com.example.android.popularmovies.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;

public class MoviesAdapter extends ListAdapter<Movie, MoviesAdapter.MoviesViewHolder> {

    //private List<Movie> movies;
    private final MoviesAdapterOnItemClickHandler clickHandler;


    public MoviesAdapter( MoviesAdapterOnItemClickHandler clickHandler) {
        super(diffCallback);
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Movie> diffCallback = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem == newItem;
        }
    };


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MoviesViewHolder(inflater.inflate(R.layout.movies_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie currentMovie = getItem(position);
        String imageUrl = null;
        if (currentMovie.getPosterPath() != null) {
            imageUrl = currentMovie.getPosterPath();
        }
        String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w185";
        Glide.with(holder.itemView.getContext())
                .load(BASE_POSTER_PATH +imageUrl)
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.poster);
    }



    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView poster;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.iv_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = getItem(adapterPosition);
            clickHandler.onItemClick(clickedMovie);
        }

    }
    /**
     * The interface that receives onItemClick messages.
     */
    public interface MoviesAdapterOnItemClickHandler {

        void onItemClick(Movie movie);
    }

}
