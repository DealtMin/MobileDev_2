package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.belkinaed.recipeheaven.R;
import ru.mirea.belkinaed.recipeheaven.data.repository.UserRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogOutUserUseCase;

public class MainActivity extends AppCompatActivity {

    private LogOutUserUseCase logOutUserUseCase;

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

        UserRepository userRepository = new UserRepositoryImpl(this);
        logOutUserUseCase = new LogOutUserUseCase(userRepository);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            logOutUserUseCase.execute();
            Toast.makeText(MainActivity.this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
