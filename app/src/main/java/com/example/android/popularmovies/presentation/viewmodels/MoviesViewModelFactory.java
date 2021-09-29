package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;

    public MoviesViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            return (T) new MoviesViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
