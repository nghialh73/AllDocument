package com.example.alldocument.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocument.FileApplication;
import com.example.alldocument.data.repository.DataRepository;
import com.example.alldocument.ui.document.DocumentViewModel;
import com.example.alldocument.ui.home.HomeViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mRepository;

        public ViewModelFactory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((FileApplication) application).getRepository();
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(HomeViewModel.class)) {
               return (T) new HomeViewModel(mApplication, mRepository);
            }
            else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
                return (T) new DocumentViewModel(mApplication);
            }
            else throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
