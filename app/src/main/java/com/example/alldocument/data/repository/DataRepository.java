package com.example.alldocument.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.repository.database.FileDatabase;
import com.example.alldocument.data.repository.database.entity.FileFavoriteEntity;
import com.example.alldocument.data.repository.database.entity.FileRecentEntity;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;

    private final FileDatabase mDatabase;
    private final FileRepository mFileRepository;


    private DataRepository(final FileDatabase database, final FileRepository fileRepository) {
        mDatabase = database;
        mFileRepository = fileRepository;


    }

    public static DataRepository getInstance(final FileDatabase database, final FileRepository fileRepository) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, fileRepository);
                }
            }
        }
        return sInstance;
    }

    public ArrayList<FileModel> getAllFile() {
        return mFileRepository.getAll();
    }

    public ArrayList<FileModel> getPDFFile() {
        return mFileRepository.getPdfFile();
    }

    public ArrayList<FileModel> getWordFile() {
        return mFileRepository.getDocFile();
    }

    public ArrayList<FileModel> getExcelFile() {
        return mFileRepository.getExcelFile();
    }

    public ArrayList<FileModel> getTextFile() {
        return mFileRepository.getTextFile();
    }

    public ArrayList<FileModel> getPowerPointFile() {
        return mFileRepository.getPowerPointFile();
    }

    public ArrayList<FileModel> getImageFile() {
        return mFileRepository.getAllFileImage();
    }

    public LiveData<List<FileRecentEntity>> getRecentFile() {
        return mDatabase.getRecentFile();
    }

    public LiveData<List<FileFavoriteEntity>> getFavoriteFile() {
        return mDatabase.getFavoriteFile();
    }
}
