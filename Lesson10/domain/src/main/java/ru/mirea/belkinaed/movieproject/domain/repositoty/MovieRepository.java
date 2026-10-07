package ru.mirea.belkinaed.movieproject.domain.repositoty;

import ru.mirea.belkinaed.movieproject.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}
