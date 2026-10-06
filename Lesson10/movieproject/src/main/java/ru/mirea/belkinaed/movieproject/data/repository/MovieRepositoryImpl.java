package ru.mirea.belkinaed.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.belkinaed.movieproject.data.storage.MovieStorage;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String SHARED_PREFS_NAME = "favorite_movie_prefs";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_MOVIE_ID = "movie_id";

    private MovieStorage storage;
    public MovieRepositoryImpl(MovieStorage storage) {
        this.storage = storage;
    }
    @Override
    public boolean saveMovie(Movie movie) {

    }

    @Override
    public Movie getMovie() {

    }
}
