package com.example.alldocument;

import android.app.Application;

import com.example.alldocument.data.repository.DataRepository;
import com.example.alldocument.data.repository.FileRepository;
import com.example.alldocument.data.repository.database.FileDatabase;

public class FileApplication extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public FileDatabase getDatabase() {
        return FileDatabase.getInstance(this, mAppExecutors);
    }

    public FileRepository getFileRepository() {
        return FileRepository.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getFileRepository());
    }
}
