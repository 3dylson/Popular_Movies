package com.example.android.popularmovies.presentation.adapters;

import static com.example.android.popularmovies.data.network.ServerValues.SUCCESS;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;

public class MoviesAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    // Two view types 1st to show a loading spinner and the other to show a post
    private static final int TYPE_LOAD = 1;
    private static final int TYPE_MOVIE = 2;

    //Loading state : ONGOING, FAILED, SUCCESS
    private Integer state;

    //private List<Movie> movies;
    private final MoviesAdapterOnItemClickHandler clickHandler;

    public MoviesAdapter( MoviesAdapterOnItemClickHandler clickHandler) {
        super(diffCallback);
        this.clickHandler = clickHandler;
    }

    public void setState(Integer state) {
        this.state = state;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_MOVIE) {
            return new MoviesViewHolder(inflater.inflate(R.layout.movies_list_items, parent, false));
        } else {
            return new LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoviesViewHolder) {
            Movie currentMovie = getItem(position);
            String imageUrl = null;
            if (currentMovie.getPosterPath() != null) {
                imageUrl = currentMovie.getPosterPath();
            }
            String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w185";
            Glide.with(holder.itemView.getContext())
                    .load(BASE_POSTER_PATH +imageUrl)
                    .fallback(R.drawable.ic_baseline_broken_image_24)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(((MoviesViewHolder) holder).poster);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount()-1 && state != null && !state.equals(SUCCESS))
            return TYPE_LOAD;
        else
            return TYPE_MOVIE;
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
            clickHandler.onMovieClick(clickedMovie);
        }

    }
    /**
     * The interface that receives onItemClick messages.
     */
    public interface MoviesAdapterOnItemClickHandler {

        void onMovieClick(Movie movie);
    }

}
