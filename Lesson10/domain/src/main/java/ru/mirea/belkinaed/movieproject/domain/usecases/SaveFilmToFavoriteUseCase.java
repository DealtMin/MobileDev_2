package ru.mirea.belkinaed.movieproject.domain.usecases;

import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;

public class SaveFilmToFavoriteUseCase {
    private final MovieRepository movieRepository;

    public SaveFilmToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(Movie movie) {
        return movieRepository.saveMovie(movie);
    }
}
