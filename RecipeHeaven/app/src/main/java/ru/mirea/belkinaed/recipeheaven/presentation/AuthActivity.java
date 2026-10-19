package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executors;

import ru.mirea.belkinaed.recipeheaven.R;
import ru.mirea.belkinaed.recipeheaven.data.repository.UserRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.CreateUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogInUserUseCase;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView tvStatus;
    private ProgressBar progressBar;

    private LogInUserUseCase logInUserUseCase;
    private CreateUserUseCase createUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);

        UserRepository userRepository = new UserRepositoryImpl();
        logInUserUseCase = new LogInUserUseCase(userRepository);
        createUserUseCase = new CreateUserUseCase(userRepository);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            navigateToMain();
        }

        btnLogin.setOnClickListener(v -> performLogin());
        btnRegister.setOnClickListener(v -> performRegister());
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            tvStatus.setText("Заполните все поля");
            return;
        }

        showLoading(true);
        tvStatus.setText("");

        Executors.newSingleThreadExecutor().execute(() -> {
            boolean success = logInUserUseCase.execute(email, password);
            runOnUiThread(() -> {
                showLoading(false);
                if (success) {
                    Toast.makeText(AuthActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                } else {
                    tvStatus.setText("Ошибка авторизации. Проверьте данные.");
                }
            });
        });
    }

    private void performRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            tvStatus.setText("Заполните все поля");
            return;
        }

        if (password.length() < 6) {
            tvStatus.setText("Пароль должен быть не менее 6 символов");
            return;
        }

        showLoading(true);
        tvStatus.setText("");

        Executors.newSingleThreadExecutor().execute(() -> {
            boolean success = createUserUseCase.execute(email, password);
            runOnUiThread(() -> {
                showLoading(false);
                if (success) {
                    Toast.makeText(AuthActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                } else {
                    tvStatus.setText("Ошибка регистрации. Пользователь уже существует или неверные данные.");
                }
            });
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!isLoading);
        btnRegister.setEnabled(!isLoading);
    }

    private void navigateToMain() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
