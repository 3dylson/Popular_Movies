package com.example.android.popularmovies.presentation.ui.fragments;


public class TopRatedFragment extends PopMovieFragment{

    @Override
    public void loadData() {
        getViewModel().getPagedListTopRatedMovie().observe(this.getViewLifecycleOwner(), movies -> getMoviesAdapter().submitList(movies));
        getViewModel().getLoadState().observe(this.getViewLifecycleOwner(), state -> getMoviesAdapter().setState(state));
    }
}