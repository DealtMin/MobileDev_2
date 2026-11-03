package ru.mirea.belkinaed.recipeheaven.presentation;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.belkinaed.recipeheaven.domain.usecases.CreateUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogInUserUseCase;

public class AuthViewModel extends ViewModel {

    private final GetUserUseCase getUserUseCase;
    private final LogInUserUseCase logInUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> navigateToMain = new MutableLiveData<>(false);

    public AuthViewModel(GetUserUseCase getUserUseCase,
                         LogInUserUseCase logInUserUseCase,
                         CreateUserUseCase createUserUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.logInUserUseCase = logInUserUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getNavigateToMain() {
        return navigateToMain;
    }

    public void checkUserLoggedIn() {
        if (getUserUseCase.execute() != null) {
            navigateToMain.setValue(true);
        }
    }

    public void loginAsGuest() {
        navigateToMain.setValue(true);
    }

    public void login(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            statusMessage.setValue("Заполните все поля");
            return;
        }

        isLoading.setValue(true);
        statusMessage.setValue("");

        executor.execute(() -> {
            boolean success = logInUserUseCase.execute(email, password);
            isLoading.postValue(false);
            if (success) {
                statusMessage.postValue("Успешный вход!");
                navigateToMain.postValue(true);
            } else {
                statusMessage.postValue("Ошибка авторизации. Проверьте данные.");
            }
        });
    }

    public void register(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            statusMessage.setValue("Заполните все поля");
            return;
        }

        if (password.length() < 6) {
            statusMessage.setValue("Пароль должен быть не менее 6 символов");
            return;
        }

        isLoading.setValue(true);
        statusMessage.setValue("");

        executor.execute(() -> {
            boolean success = createUserUseCase.execute(email, password);
            isLoading.postValue(false);
            if (success) {
                statusMessage.postValue("Регистрация успешна!");
                navigateToMain.postValue(true);
            } else {
                statusMessage.postValue("Ошибка регистрации. Пользователь уже существует или неверные данные.");
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
