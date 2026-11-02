package ru.mirea.belkinaed.lesson10;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MainViewModel(
    ) { /* Init ViewModel here */
        Log.d(MainViewModel.class.getSimpleName().toString(), "MainViewModel created");
    }
    @Override
    protected void onCleared() {
        Log.d(MainViewModel.class.getSimpleName().toString(), " MainViewModel cleared");
        super.onCleared();
    }
}
