package com.example.android.popularmovies.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ReviewViewHolder> {

    private final ReviewAdapterOnItemClickHandler clickHandler;


    public ReviewsAdapter(ReviewAdapterOnItemClickHandler clickHandler) {
        super(diffCallback);
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Review> diffCallback = new DiffUtil.ItemCallback<Review>() {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem == newItem;
        }
    };

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReviewViewHolder(inflater.inflate(R.layout.reviews_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = getItem(position);
        String authorName = "Anonymous";

        int rateStar = 0;
        int authorRate = currentReview.getAuthorDetails().getRating();
        if (authorRate <= 2) {
            rateStar = 1;
        }
        if (authorRate >= 3 && authorRate <= 4 ) {
            rateStar = 2;
        }
        if (authorRate >= 5 && authorRate <= 6 ) {
            rateStar = 3;
        }
        if (authorRate >= 7 && authorRate <= 8 ) {
            rateStar = 4;
        }
        if (authorRate >= 9 && authorRate <= 10 ) {
            rateStar = 5;
        }

        if (currentReview.getAuthor() != null) {
            authorName = currentReview.getAuthor();
        }
        holder.authorName.setText(authorName);

        String AVATAR_PATH = "http://image.tmdb.org/t/p/w185";
        String avatarpath = currentReview.getAuthorDetails().getAvatarPath();

        if (avatarpath != null && !avatarpath.startsWith("/https")){
            avatarpath = AVATAR_PATH+avatarpath;
        }
        else if (avatarpath != null && avatarpath.startsWith("/https")){
            avatarpath = avatarpath.substring(1);
        }


        Glide.with(holder.itemView.getContext())
                .load(avatarpath)
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .circleCrop()
                .fitCenter()
                .into(holder.authorAvatar);

        holder.starRate.setRating(rateStar);
        holder.createdAt.setText(currentReview.getCreatedAt());
        holder.review.setText(currentReview.getContent());
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView authorAvatar;
        final TextView authorName;
        final RatingBar starRate;
        final TextView createdAt;
        final TextView review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            authorAvatar = itemView.findViewById(R.id.im_authorAvatar);
            authorName = itemView.findViewById(R.id.tv_authorName);
            starRate = itemView.findViewById(R.id.rating);
            createdAt = itemView.findViewById(R.id.tv_createdAt);
            review = itemView.findViewById(R.id.tv_review);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onReviewClick(adapterPosition);

        }
    }

    public interface ReviewAdapterOnItemClickHandler {
        void onReviewClick(int adapterPosition);
    }

}
