package ru.mirea.belkinaed.recipeheaven.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.belkinaed.recipeheaven.data.storage.UserStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.models.User;

public class SharedPrefUserStorage implements UserStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_user";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_DESCRIPTION = "user_description";

    private final SharedPreferences sharedPreferences;

    public SharedPrefUserStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(User user) {
        if (user == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_DESCRIPTION, user.getDescription());
        return editor.commit();
    }

    @Override
    public User get() {
        String name = sharedPreferences.getString(KEY_NAME, null);
        if (name == null || name.isEmpty()) {
            return null;
        }
        String description = sharedPreferences.getString(KEY_DESCRIPTION, "");
        return new User(name, description);
    }

    @Override
    public boolean createUser(User user) {
        return save(user);
    }

    @Override
    public boolean createUser(String email, String password) {
        return true;
    }

    @Override
    public boolean logIn(String email, String password) {
        return true;
    }

    @Override
    public boolean logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }
}
