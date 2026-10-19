package ru.mirea.belkinaed.recipeheaven.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final FirebaseAuth mAuth;

    public UserRepositoryImpl() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public UserRepositoryImpl(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @Override
    public User getUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            int id = firebaseUser.getUid().hashCode();
            String name = firebaseUser.getDisplayName() != null && !firebaseUser.getDisplayName().isEmpty()
                    ? firebaseUser.getDisplayName()
                    : (firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "User");
            String email = firebaseUser.getEmail();
            return new User(name, "Firebase User", email);
        }
        return null;
    }

    @Override
    public boolean UpdateUser(User data) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null || data == null) {
            return false;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] result = new boolean[1];

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(data.getName())
                .build();

        user.updateProfile(profileUpdates)
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
    public boolean CreateUser(User data) {
        if (data == null) return false;
        String email = data.getEmail();
        String password = data.getPassword();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        return CreateUser(email, password, data.getName());
    }

    @Override
    public boolean CreateUser(String email, String password) {
        return CreateUser(email, password, null);
    }

    private boolean CreateUser(String email, String password, String displayName) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return false;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] result = new boolean[1];

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Executors.newSingleThreadExecutor(), task -> {
                    if (task.isSuccessful()) {
                        result[0] = true;
                        if (displayName != null && !displayName.isEmpty()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .build();
                                user.updateProfile(profileUpdates);
                            }
                        }
                    } else {
                        result[0] = false;
                    }
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
    public boolean LogIn(String email, String password) {
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
    public boolean LogOut() {
        try {
            mAuth.signOut();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
