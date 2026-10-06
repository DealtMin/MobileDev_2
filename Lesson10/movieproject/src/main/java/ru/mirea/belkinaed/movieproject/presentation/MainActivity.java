package ru.mirea.belkinaed.movieproject.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.belkinaed.movieproject.R;
import ru.mirea.belkinaed.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.belkinaed.movieproject.data.storage.MovieStorage;
import ru.mirea.belkinaed.movieproject.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;
import ru.mirea.belkinaed.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.belkinaed.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText text = findViewById(R.id.editTextMovie);
        TextView textView = findViewById(R.id.textViewMovie);
        MovieStorage sharedPrefMovieStorage = new SharedPrefMovieStorage(this);
        MovieRepository movieRepository = new MovieRepositoryImpl(sharedPrefMovieStorage);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(view -> {
            Boolean result = new SaveFilmToFavoriteUseCase(movieRepository).execute(
                    new Movie(2, text.getText().toString())
            );
            textView.setText("Saved");
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(view -> {
            Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
            textView.setText(String.format("Save result %s", movie.getName()));
        });
    }
}
