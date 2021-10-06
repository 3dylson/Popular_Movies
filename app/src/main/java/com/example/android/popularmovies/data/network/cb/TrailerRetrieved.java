package com.example.android.popularmovies.data.network.cb;

import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;

public interface TrailerRetrieved {

    void onTrailerFetchedSuccess(TrailerResponse response);

    void onTrailerFetchedFailed();
}
