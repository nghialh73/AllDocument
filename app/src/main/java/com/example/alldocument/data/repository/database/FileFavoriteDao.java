package com.example.alldocument.data.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alldocument.data.repository.database.entity.FileFavoriteEntity;

import java.util.List;
@Dao
public interface FileFavoriteDao {
    @Query("SELECT * FROM file_favorite WHERE favorite = 1  ORDER BY time_last_open DESC")
    LiveData<List<FileFavoriteEntity>> getAllFavorite();

    @Insert
    void insertFileFavorite(List<FileFavoriteEntity> files);

    @Query("UPDATE file_favorite SET time_last_open = :time_last_open, favorite = :favorite WHERE file_path = :file_path")
    void updateFileFavorite(
            String file_path,
            Long time_last_open,
            Boolean favorite
    );

    @Delete
    void deleteFileFavorite(List<FileFavoriteEntity> files);

    @Query("DELETE FROM file_favorite WHERE file_path = :path")
    void deleteFileFavorite(String path);

    @Query("SELECT EXISTS(SELECT * FROM file_favorite WHERE file_path = :file_path)")
    Boolean isRowIsExist(String file_path);
}
