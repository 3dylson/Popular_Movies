package com.example.android.popularmovies.presentation.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.presentation.adapters.MoviesAdapter;
import com.example.android.popularmovies.presentation.ui.DetailsActivity;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModel;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModelFactory;


public class PopMovieFragment extends Fragment implements MoviesAdapter.MoviesAdapterOnItemClickHandler{


    private MoviesViewModel viewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;



    public PopMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = requireView().findViewById(R.id.rv_movies);
        progressBar = requireView().findViewById(R.id.pb_loading_indicator);

        initRecyclerView();
        setupAdapter();
        loadData();

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public MoviesAdapter getMoviesAdapter() {
        return moviesAdapter;
    }

    public MoviesViewModel getViewModel() {
        return viewModel;
    }


    public void loadData() {
        viewModel.getPagedListPopMovie().observe(this.getViewLifecycleOwner(), movies -> moviesAdapter.submitList(movies));
        viewModel.getLoadState().observe(this.getViewLifecycleOwner(), state -> moviesAdapter.setState(state));
    }


    public void setupAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
    }


    private void initRecyclerView() {
        GridLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // landscape
            layoutManager
                    = new GridLayoutManager(requireContext(),6);

        } else {
            // portrait
            layoutManager
                    = new GridLayoutManager(requireContext(),3);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        MoviesViewModelFactory factory = new MoviesViewModelFactory(this.requireActivity().getApplication());
        viewModel = new ViewModelProvider(this,factory).get(MoviesViewModel.class);

    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent movieDetailIntent = new Intent(requireActivity(), DetailsActivity.class);
        movieDetailIntent.putExtra(DetailsActivity.EXTRA_MOVIE,movie);
        startActivity(movieDetailIntent);
    }
}