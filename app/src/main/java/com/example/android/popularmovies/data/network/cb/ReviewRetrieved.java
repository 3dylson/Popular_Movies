package com.example.android.popularmovies.data.network.cb;

import com.example.android.popularmovies.data.network.responsemodel.ReviewResponse;

public interface ReviewRetrieved {

    void onReviewFetchedSuccess(ReviewResponse response);

    void onReviewFetchedFailed();
}
