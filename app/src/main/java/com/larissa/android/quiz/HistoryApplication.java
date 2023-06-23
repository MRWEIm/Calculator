package com.larissa.android.quiz;

import android.app.Application;

public class HistoryApplication extends Application {
    private HistoryRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        HistoryRepository.initialize(this);
        repository=HistoryRepository.getInstance();
    }
}
