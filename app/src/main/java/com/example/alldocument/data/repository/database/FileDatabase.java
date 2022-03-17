package com.example.alldocument.data.repository.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.alldocument.AppExecutors;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.repository.database.entity.FileFavoriteEntity;
import com.example.alldocument.data.repository.database.entity.FileRecentEntity;

import java.util.List;

@Database(entities = {FileFavoriteEntity.class, FileRecentEntity.class}, version = 1, exportSchema = false)
public abstract class FileDatabase extends RoomDatabase{
    private static FileDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "file_db";

    public abstract FileFavoriteDao favoriteDao();

    public abstract FileRecentDao recentDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static FileDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (FileDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                }
            }
        }
        return sInstance;
    }

    private static FileDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, FileDatabase.class, DATABASE_NAME)
                .build();
    }

    public LiveData<List<FileRecentEntity>> getRecentFile() {
        return recentDao().getAllRecentFileSave();
    }

    public LiveData<List<FileFavoriteEntity>> getFavoriteFile() {
        return favoriteDao().getAllFavorite();
    }
}
