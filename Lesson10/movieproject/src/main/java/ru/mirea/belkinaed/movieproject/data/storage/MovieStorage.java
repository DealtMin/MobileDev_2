package ru.mirea.belkinaed.movieproject.data.storage;


import ru.mirea.belkinaed.movieproject.data.storage.models.Movie;

public interface MovieStorage {
    public Movie get();
    public boolean save(Movie movie);
}
