package com.example.alldocument.ui.document;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.data.repository.DataRepository;

import java.util.List;

public class DocumentViewModel extends AndroidViewModel {
    private final MutableLiveData<List<FileModel>> mObservable = new MutableLiveData<>();
    private DataRepository mRepository;

    public DocumentViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        this.mRepository = repository;
    }

    public void setData(HomeItemType type) {
        switch (type) {
            case ALL:
                mObservable.setValue(mRepository.getAllFile());
                break;
            case PDF:
                mObservable.setValue(mRepository.getPDFFile());
                break;
            case POWER_POINT:
                mObservable.setValue(mRepository.getPowerPointFile());
                break;
            case EXCEL:
                mObservable.setValue(mRepository.getExcelFile());
                break;
            case WORD:
                mObservable.setValue(mRepository.getWordFile());
                break;
            case TEXT:
                mObservable.setValue(mRepository.getTextFile());
                break;
            case SCREEN:
                mObservable.setValue(mRepository.getImageFile());
                break;
            case RECENT:
                //mObservable.setValue(mRepository.getRecentFile().getValue());
                break;
            case FAVORITE:
                //mObservable.setValue(mRepository.getFavoriteFile().getValue());
                break;
        }
    }

    public LiveData<List<FileModel>> getData() {
        return mObservable;
    }
}
