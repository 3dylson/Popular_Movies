package com.example.android.popularmovies.presentation.ui.fragments;


public class TopRatedFragment extends PopMovieFragment{

    @Override
    public void loadData() {
        getViewModel().getPagedListTopRatedMovie().observe(this.getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                showData();
                getMoviesAdapter().submitList(movies);
            }
            else {
                showLoading();
            }
        });
        getViewModel().getLoadState().observe(this.getViewLifecycleOwner(), state -> getMoviesAdapter().setState(state));
    }
}