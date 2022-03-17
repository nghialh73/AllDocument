package com.example.alldocument.data.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.File;
import java.io.Serializable;

@Entity(tableName = "pdf_table")
public class FileModel implements Serializable{
    @NonNull
    @PrimaryKey()
    private String path;
    private String name;
    private long size;
    private long date;
    private int recent = 0;
    private int favorite = 0;
    private long updateAt;
    private int page = 1;
    private HomeItemType type;
    @Ignore
    private transient Bitmap avatar;
    public FileModel() {
    }

    public FileModel(@NonNull String path, String name, long size, long date, long updateAt, HomeItemType type) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.date = date;
        this.updateAt = updateAt;
        this.type = type;
    }

    public int getRecent() {
        return recent;
    }

    public void setRecent(int recent) {
        this.recent = recent;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }


    public void setSize(long size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public File getFile() {
        return new File(getPath());
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}

