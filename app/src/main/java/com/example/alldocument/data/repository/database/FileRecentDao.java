package com.example.alldocument.data.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alldocument.data.repository.database.entity.FileFavoriteEntity;
import com.example.alldocument.data.repository.database.entity.FileRecentEntity;

import java.util.List;

@Dao
public interface FileRecentDao {
    @Query("SELECT * FROM file_save ORDER BY time_last_open DESC")
    LiveData<List<FileRecentEntity>> getAllRecentFileSave();

    @Insert
    void insertFileSave(List<FileRecentEntity> files);

    @Query("UPDATE file_save SET time_last_open = :time_last_open, favorite = :favorite WHERE file_path = :file_path")
    void updateFileSave(
            String file_path ,
            Long time_last_open ,
            Boolean favorite
    );

    @Query("SELECT EXISTS(SELECT * FROM file_save WHERE file_path = :file_path)")
    Boolean isRowIsExist(String file_path ) ;

    @Delete
    void deleteFileSave(List<FileRecentEntity> files);
}
