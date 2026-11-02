package ru.mirea.belkinaed.movieproject.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.movieproject.R;
import ru.mirea.belkinaed.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.belkinaed.movieproject.data.storage.MovieStorage;
import ru.mirea.belkinaed.movieproject.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.belkinaed.movieproject.domain.models.Movie;
import ru.mirea.belkinaed.movieproject.domain.repositoty.MovieRepository;
import ru.mirea.belkinaed.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.belkinaed.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText text = findViewById(R.id.editTextMovie);
        TextView textView = findViewById(R.id.textViewMovie);

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(this)).get(MainViewModel.class);
        mainViewModel.getFavoriteMovie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        findViewById(R.id.buttonSaveMovie).setOnClickListener(new
          View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  mainViewModel.setText(new Movie(2, text.getText().toString()));
              }
          });
        findViewById(R.id.buttonGetMovie).setOnClickListener(new
             View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     mainViewModel.getText();
                 }
             });
    }
}
