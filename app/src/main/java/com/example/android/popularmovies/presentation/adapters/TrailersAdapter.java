package com.example.android.popularmovies.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.detabase.entity.Trailer;

public class TrailersAdapter extends ListAdapter<Trailer, TrailersAdapter.TrailerViewHolder> {

    private final TrailerAdapterOnItemClickHandler clickHandler;

    public TrailersAdapter(TrailerAdapterOnItemClickHandler clickHandler) {
        super(diffCallback);
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Trailer> diffCallback = new DiffUtil.ItemCallback<Trailer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
            return oldItem == newItem;
        }
    };



    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TrailerViewHolder(inflater.inflate(R.layout.trailers_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.TrailerViewHolder holder, int position) {
        Trailer currentTrailer = getItem(position);
        String YOUTUBE_URL = "https://img.youtube.com/vi/";
        Glide.with(holder.itemView.getContext())
                .load(YOUTUBE_URL +currentTrailer.getKey()+"/0.jpg")
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.videoThumb);
        holder.trailerTitle.setText(currentTrailer.getName());
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView videoThumb;
        final TextView trailerTitle;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            videoThumb = itemView.findViewById(R.id.video_thumb);
            trailerTitle = itemView.findViewById(R.id.trailer_title);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer clickedTrailer = getItem(adapterPosition);
            clickHandler.onItemClick(clickedTrailer);
        }
    }

    public interface TrailerAdapterOnItemClickHandler {
        void onItemClick(Trailer trailer);
    }

}
