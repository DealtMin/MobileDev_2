package ru.mirea.belkinaed.movieproject.presentation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.belkinaed.movieproject.domain.models.Movie;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;
import ru.mirea.belkinaed.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.belkinaed.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();
    public MutableLiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }
    public MainViewModel(MovieRepository movieRepository) {
        Log.d(MainViewModel.class.getSimpleName().toString(), "MainViewModel created");
        this.movieRepository = movieRepository;
    }
    @Override
    protected void onCleared() {
        Log.d(MainViewModel.class.getSimpleName().toString(), "MainViewModel cleared");
        super.onCleared();
    }
    public void setText(Movie movie){
        Boolean result = new
                SaveFilmToFavoriteUseCase(movieRepository).execute(movie);
        favoriteMovie.setValue(result.toString());
    }
    public void getText(){
        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        favoriteMovie.setValue(String.format("My favorite movie is %s",
                movie.getName()));
    }
}
