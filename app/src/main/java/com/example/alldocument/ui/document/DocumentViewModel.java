package com.example.alldocument.ui.document;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.repository.DataRepository;
import com.example.alldocument.data.repository.database.entity.FileFavoriteEntity;
import com.example.alldocument.data.repository.database.entity.FileRecentEntity;

import java.util.List;

public class DocumentViewModel extends AndroidViewModel {

    private final MutableLiveData<List<FileModel>> mObservableAll = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservablePDF = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservablePPT = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservableExcel = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservableText = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservableImage = new MutableLiveData<>();
    private final MutableLiveData<List<FileModel>> mObservableWord = new MutableLiveData<>();
    private LiveData<List<FileRecentEntity>> mObservableRecent;
    private LiveData<List<FileFavoriteEntity>> mObservableFavorite;

    public DocumentViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        setAllData(repository);
        setPDFData(repository);
        setWordData(repository);
        setExcelData(repository);
        setTextData(repository);
        setPowerPointData(repository);
        setImageData(repository);
        setRecentData(repository);
        setFavoriteData(repository);
    }

    public void setAllData(DataRepository repository) {
        mObservableAll.setValue(repository.getAllFile());
    }

    public void setPDFData(DataRepository repository) {
        mObservablePDF.setValue(repository.getPDFFile());
    }

    public void setWordData(DataRepository repository) {
        mObservableWord.setValue(repository.getWordFile());
    }

    public void setExcelData(DataRepository repository) {
        mObservableExcel.setValue(repository.getExcelFile());
    }

    public void setTextData(DataRepository repository) {
        mObservableText.setValue(repository.getTextFile());
    }

    public void setPowerPointData(DataRepository repository) {
        mObservablePPT.setValue(repository.getPowerPointFile());
    }

    public void setImageData(DataRepository repository) {
        mObservableImage.setValue(repository.getImageFile());
    }

    public void setRecentData(DataRepository repository) {
        mObservableRecent = repository.getRecentFile();
    }

    public void setFavoriteData(DataRepository repository) {
        mObservableFavorite = repository.getFavoriteFile();
    }

    public LiveData<List<FileModel>> getAllData() {
        return mObservableAll;
    }

    public LiveData<List<FileModel>> getPDFData() {
        return mObservablePDF;
    }

    public LiveData<List<FileModel>> getWordData() {
        return mObservableWord;
    }

    public LiveData<List<FileModel>> getExcelData() {
        return mObservableExcel;
    }

    public LiveData<List<FileModel>> getTextData() {
        return mObservableText;
    }

    public LiveData<List<FileModel>> getPowerPointData() {
        return mObservablePPT;
    }

    public LiveData<List<FileModel>> getImageData() {
        return mObservableImage;
    }

    public LiveData<List<FileRecentEntity>> getRecentData() {
        return mObservableRecent;
    }

    public LiveData<List<FileFavoriteEntity>> getFavoriteData() {
        return mObservableFavorite;
    }
}
