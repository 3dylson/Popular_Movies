package com.example.android.popularmovies.presentation.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> optionsMenuCreated = new MutableLiveData<>();
    private boolean isPopMovieFragment = true;
    private boolean isTopMovieFragment = false;
    private boolean isFavMovieFragment = false;

    public MutableLiveData<Boolean> getOptionsMenuCreated() {
        return optionsMenuCreated;
    }

    public boolean isPopMovieFragment() {
        return isPopMovieFragment;
    }

    public void setPopMovieFragment(boolean popMovieFragment) {
        isPopMovieFragment = popMovieFragment;
    }

    public boolean isTopMovieFragment() {
        return isTopMovieFragment;
    }

    public void setTopMovieFragment(boolean topMovieFragment) {
        isTopMovieFragment = topMovieFragment;
    }

    public boolean isFavMovieFragment() {
        return isFavMovieFragment;
    }

    public void setFavMovieFragment(boolean favMovieFragment) {
        isFavMovieFragment = favMovieFragment;
    }
}
