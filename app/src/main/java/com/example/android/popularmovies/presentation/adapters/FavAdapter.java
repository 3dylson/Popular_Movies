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
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;

public class FavAdapter extends ListAdapter<MoviePersisted, FavAdapter.FavViewHolder> {

    private final FavAdapterOnItemClickHandler clickHandler;

    public FavAdapter(FavAdapterOnItemClickHandler clickHandler) {
        super(diffCallback);
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<MoviePersisted> diffCallback = new DiffUtil.ItemCallback<MoviePersisted>() {
        @Override
        public boolean areItemsTheSame(@NonNull MoviePersisted oldItem, @NonNull MoviePersisted newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MoviePersisted oldItem, @NonNull MoviePersisted newItem) {
            return oldItem == newItem;
        }
    };

    @NonNull
    @Override
    public FavAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FavViewHolder(inflater.inflate(R.layout.movies_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FavViewHolder holder, int position) {
        MoviePersisted currentMovie = getItem(position);
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
                .into(holder.poster);
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView poster;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getBindingAdapterPosition();
            MoviePersisted clickedMovie = getItem(adapterPosition);
            clickHandler.onFavMovieCLick(clickedMovie);
        }
    }

    public interface FavAdapterOnItemClickHandler {
        void onFavMovieCLick(MoviePersisted moviePersisted);
    }
}
