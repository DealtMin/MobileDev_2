package ru.mirea.belkinaed.movieproject.data.repository;

import android.annotation.SuppressLint;

import java.time.LocalDate;

import ru.mirea.belkinaed.movieproject.data.storage.MovieStorage;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String SHARED_PREFS_NAME = "favorite_movie_prefs";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_MOVIE_ID = "movie_id";

    private MovieStorage sharedPrefMovieStorage;
    public MovieRepositoryImpl(MovieStorage storage) {
        this.sharedPrefMovieStorage = storage;
    }
    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean saveMovie(Movie movie){
        sharedPrefMovieStorage.save(mapToStorage(movie));
        return true;
    }
    @Override
    public Movie getMovie(){
        ru.mirea.belkinaed.movieproject.data.storage.models.Movie movie = sharedPrefMovieStorage.get();
        return mapToDomain(movie);
    }
    private ru.mirea.belkinaed.movieproject.data.storage.models.Movie mapToStorage(Movie movie){
        String name = movie.getName();
        return new ru.mirea.belkinaed.movieproject.data.storage.models.Movie
                (2, name, LocalDate.now().toString());
    }
    private Movie mapToDomain(ru.mirea.belkinaed.movieproject.data.storage.models.Movie movie){
        String name = movie.getName();
        return new Movie(movie.getId(), movie.getName());
    }
}
