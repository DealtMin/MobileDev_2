package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.recipeheaven.R;
import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private TextView tvWeather;
    private TextView tvRecipes;
    private Button btnLogout;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvWeather = findViewById(R.id.tvWeather);
        tvRecipes = findViewById(R.id.tvRecipes);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogin = findViewById(R.id.btnLogin);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this)).get(MainViewModel.class);

        viewModel.getIsUserLoggedIn().observe(this, isLoggedIn -> {
            if (Boolean.TRUE.equals(isLoggedIn)) {
                btnLogout.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
            } else {
                btnLogout.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getCombinedDataLiveData().observe(this, combinedData -> {
            if (combinedData != null) {
                tvWeather.setText("Текущая погода: " + combinedData.getWeather());

                StringBuilder sb = new StringBuilder();
                if (combinedData.getRecipes() != null && !combinedData.getRecipes().isEmpty()) {
                    for (Recipe recipe : combinedData.getRecipes()) {
                        sb.append("- ").append(recipe.getName())
                                .append(" (").append(recipe.getType()).append(")")
                                .append("  Инструкция: ").append(recipe.getSteps()).append("\n\n");
                    }
                } else {
                    sb.append("Нет рецептов.");
                }
                tvRecipes.setText(sb.toString().trim());
            }
        });

        viewModel.getLogOutSuccess().observe(this, isLoggedOut -> {
            if (Boolean.TRUE.equals(isLoggedOut)) {
                Toast.makeText(MainActivity.this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(v -> viewModel.logout());

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.checkUserStatus();
        }
    }
}
