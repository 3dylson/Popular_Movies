package com.example.android.popularmovies.data.detabase;

public interface FavoriteAction {

    void onFavAddSuccess();

    void onFavAddFail(String error);

    void onDeleteFavSuccess();

    void onDeleteFavFail(String error);
}
