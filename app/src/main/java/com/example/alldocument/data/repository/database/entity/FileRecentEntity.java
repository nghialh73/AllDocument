package com.example.alldocument.data.repository.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "file_save")
public class FileRecentEntity {
    @PrimaryKey(autoGenerate = true)
    private long id_file;
    @ColumnInfo(name = "file_name")
    private String file_name;
    @ColumnInfo(name = "file_path")
    private String file_path;
    @ColumnInfo(name = "type_file")
    private int type_file;
    @ColumnInfo(name = "time_added")
    private long time_added;
    @ColumnInfo(name = "time_modified")
    private long time_modified;
    @ColumnInfo(name = "time_last_open")
    private long time_last_open;
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public FileRecentEntity() {
    }

    public long getId_file() {
        return id_file;
    }

    public void setId_file(long id_file) {
        this.id_file = id_file;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getType_file() {
        return type_file;
    }

    public void setType_file(int type_file) {
        this.type_file = type_file;
    }

    public long getTime_added() {
        return time_added;
    }

    public void setTime_added(long time_added) {
        this.time_added = time_added;
    }

    public long getTime_modified() {
        return time_modified;
    }

    public void setTime_modified(long time_modified) {
        this.time_modified = time_modified;
    }

    public long getTime_last_open() {
        return time_last_open;
    }

    public void setTime_last_open(long time_last_open) {
        this.time_last_open = time_last_open;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
