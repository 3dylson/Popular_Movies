package com.example.android.popularmovies.presentation.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.network.ServerValues;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.presentation.adapters.MoviesAdapter;
import com.example.android.popularmovies.presentation.ui.DetailsActivity;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModel;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModelFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class PopMovieFragment extends Fragment implements MoviesAdapter.MoviesAdapterOnItemClickHandler{


    private MoviesViewModel viewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private TextView emptyLabel, errorLabel;


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
        emptyLabel = requireView().findViewById(R.id.empty_label);
        errorLabel = requireView().findViewById(R.id.error_label);
        showLoading();
        initRecyclerView();
        setupAdapter();
        loadData();

    }

    TextView getErrorLabel() {
        return errorLabel;
    }

    ProgressBar getProgressBar() {
        return progressBar;
    }

    TextView getEmptyLabel() {
        return emptyLabel;
    }

    RecyclerView getRecyclerView() {
        return recyclerView;
    }

    MoviesAdapter getMoviesAdapter() {
        return moviesAdapter;
    }

    MoviesViewModel getViewModel() {
        return viewModel;
    }


    void loadData() {
        viewModel.getPagedListPopMovie().observe(this.getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                moviesAdapter.submitList(movies);
                showData();
            }
            else {
                showLoading();
            }
        });
        viewModel.getPopLoadState().observe(this.getViewLifecycleOwner(), state -> {
            moviesAdapter.setState(state);
            if (state.equals(ServerValues.FAILED) && moviesAdapter.getItemCount() == 0) {
                showError();
            }
        });
    }


    void setupAdapter() {
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

    void showData() {
        emptyLabel.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        errorLabel.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        emptyLabel.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorLabel.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    void showError() {
        emptyLabel.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        errorLabel.setVisibility(View.VISIBLE);
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