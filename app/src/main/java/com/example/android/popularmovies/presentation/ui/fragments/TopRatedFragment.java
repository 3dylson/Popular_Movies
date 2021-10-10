package com.example.android.popularmovies.presentation.ui.fragments;


import com.example.android.popularmovies.data.network.ServerValues;

public class TopRatedFragment extends PopMovieFragment{

    @Override
    public void loadData() {
        getViewModel().getPagedListTopRatedMovie().observe(this.getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                getMoviesAdapter().submitList(movies);
                showData();
            }
            else {
                showLoading();
            }
        });
        getViewModel().getTopLoadState().observe(this.getViewLifecycleOwner(), state -> {
            getMoviesAdapter().setState(state);
            if (state.equals(ServerValues.FAILED) && getMoviesAdapter().getItemCount() == 0) {
                showError();
            }
        });
    }
}