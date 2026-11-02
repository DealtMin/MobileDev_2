package ru.mirea.belkinaed.recipeheaven.data.repository;

import android.content.Context;

import ru.mirea.belkinaed.recipeheaven.data.storage.UserStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.firebase.FirebaseUserStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.sharedprefs.SharedPrefUserStorage;
import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final UserStorage userStorage;
    private final UserStorage authStorage;

    public UserRepositoryImpl() {
        this.userStorage = null;
        this.authStorage = new FirebaseUserStorage();
    }

    public UserRepositoryImpl(Context context) {
        this.userStorage = new SharedPrefUserStorage(context);
        this.authStorage = new FirebaseUserStorage();
    }

    public UserRepositoryImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
        this.authStorage = new FirebaseUserStorage();
    }

    public UserRepositoryImpl(UserStorage userStorage, UserStorage authStorage) {
        this.userStorage = userStorage;
        this.authStorage = authStorage;
    }

    @Override
    public User getUser() {
        if (userStorage != null) {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.User storageUser = userStorage.get();
            if (storageUser != null && storageUser.getName() != null && !storageUser.getName().isEmpty()) {
                String email = storageUser.getEmail();
                String password = storageUser.getPassword();
                if (authStorage != null) {
                    ru.mirea.belkinaed.recipeheaven.data.storage.models.User authUser = authStorage.get();
                    if (authUser != null && authUser.getEmail() != null) {
                        email = authUser.getEmail();
                        if (password == null) {
                            password = authUser.getPassword();
                        }
                    }
                }
                return new User(storageUser.getName(), email, password);
            }
        }

        if (authStorage != null) {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.User authUser = authStorage.get();
            if (authUser != null) {
                return mapToDomain(authUser);
            }
        }
        return null;
    }

    @Override
    public boolean UpdateUser(User data) {
        if (data == null) {
            return false;
        }

        boolean localSaved = true;
        if (userStorage != null) {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.User storageUser = mapToStorage(data);
            localSaved = userStorage.save(storageUser);
        }

        boolean authSaved = true;
        if (authStorage != null) {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.User storageUser = mapToStorage(data);
            authSaved = authStorage.save(storageUser);
        }

        return localSaved && authSaved;
    }

    @Override
    public boolean CreateUser(User data) {
        if (data == null) return false;

        if (userStorage != null && data.getName() != null && !data.getName().isEmpty()) {
            userStorage.save(new ru.mirea.belkinaed.recipeheaven.data.storage.models.User(data.getName(), "Registered User"));
        }

        if (authStorage != null) {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.User storageUser = mapToStorage(data);
            return authStorage.createUser(storageUser);
        }
        return false;
    }

    @Override
    public boolean CreateUser(String email, String password) {
        if (authStorage != null) {
            return authStorage.createUser(email, password);
        }
        return false;
    }

    @Override
    public boolean LogIn(String email, String password) {
        if (authStorage != null) {
            return authStorage.logIn(email, password);
        }
        return false;
    }

    @Override
    public boolean LogOut() {
        boolean result = true;
        if (userStorage != null) {
            result = userStorage.logOut();
        }
        if (authStorage != null) {
            result = authStorage.logOut() && result;
        }
        return result;
    }

    private User mapToDomain(ru.mirea.belkinaed.recipeheaven.data.storage.models.User storageUser) {
        if (storageUser == null) return null;
        return new User(storageUser.getName(), storageUser.getEmail(), storageUser.getPassword());
    }

    private ru.mirea.belkinaed.recipeheaven.data.storage.models.User mapToStorage(User domainUser) {
        if (domainUser == null) return null;
        return new ru.mirea.belkinaed.recipeheaven.data.storage.models.User(
                domainUser.getName(),
                "Registered User",
                domainUser.getEmail(),
                domainUser.getPassword()
        );
    }
}
