package ru.mirea.belkinaed.recipeheaven.data.storage.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ru.mirea.belkinaed.recipeheaven.data.storage.UserStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.models.User;

public class FirebaseUserStorage implements UserStorage {

    private final FirebaseAuth mAuth;

    public FirebaseUserStorage() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUserStorage(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @Override
    public User get() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            String email = firebaseUser.getEmail();
            return new User(null, null, email, null);
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        if (user == null) {
            return false;
        }
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public boolean createUser(User user) {
        if (user == null) return false;
        String email = user.getEmail();
        String password = user.getPassword();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        return createUserWithEmailAndPassword(email, password);
    }

    @Override
    public boolean createUser(String email, String password) {
        return createUserWithEmailAndPassword(email, password);
    }

    private boolean createUserWithEmailAndPassword(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] result = new boolean[1];

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    result[0] = task.isSuccessful();
                    latch.countDown();
                });

        try {
            latch.await(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        return result[0];
    }

    @Override
    public boolean logIn(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] result = new boolean[1];

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    result[0] = task.isSuccessful();
                    latch.countDown();
                });

        try {
            latch.await(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        return result[0];
    }

    @Override
    public boolean logOut() {
        try {
            mAuth.signOut();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
