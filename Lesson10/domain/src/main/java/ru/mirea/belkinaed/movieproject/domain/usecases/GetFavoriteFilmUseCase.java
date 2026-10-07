package ru.mirea.belkinaed.movieproject.domain.usecases;

import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;

public class GetFavoriteFilmUseCase {
    private final MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}
