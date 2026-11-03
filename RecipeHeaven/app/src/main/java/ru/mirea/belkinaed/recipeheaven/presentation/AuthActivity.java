package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.recipeheaven.R;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView tvStatus;
    private ProgressBar progressBar;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interaction with domain layer is carried out via AuthViewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        viewModel.getNavigateToMain().observe(this, shouldNavigate -> {
            if (Boolean.TRUE.equals(shouldNavigate)) {
                navigateToMain();
            }
        });

        viewModel.checkUserLoggedIn();

        setContentView(R.layout.activity_auth);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);

        // UI state updating via LiveData
        viewModel.getStatusMessage().observe(this, status -> {
            if (status != null && !status.isEmpty()) {
                if (status.equals("Успешный вход!") || status.equals("Регистрация успешна!")) {
                    Toast.makeText(AuthActivity.this, status, Toast.LENGTH_SHORT).show();
                } else {
                    tvStatus.setText(status);
                }
            } else {
                tvStatus.setText("");
            }
        });

        viewModel.getIsLoading().observe(this, isLoading ->
                showLoading(Boolean.TRUE.equals(isLoading))
        );

        btnLogin.setOnClickListener(v -> performLogin());
        btnRegister.setOnClickListener(v -> performRegister());
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        viewModel.login(email, password);
    }

    private void performRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        viewModel.register(email, password);
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!isLoading);
        btnRegister.setEnabled(!isLoading);
    }

    private void navigateToMain() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
