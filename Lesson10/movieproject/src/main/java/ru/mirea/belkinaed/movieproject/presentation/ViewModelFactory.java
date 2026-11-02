package ru.mirea.belkinaed.movieproject.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.belkinaed.movieproject.data.storage.MovieStorage;
import ru.mirea.belkinaed.movieproject.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    public ViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieStorage sharedPrefMovieStorage = new
                SharedPrefMovieStorage(context);
        MovieRepository movieRepository = new
                MovieRepositoryImpl(sharedPrefMovieStorage);
        return (T) new MainViewModel(movieRepository);
    }
}
