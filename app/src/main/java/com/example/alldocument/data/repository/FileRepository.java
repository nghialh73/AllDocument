package com.example.alldocument.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alldocument.AppExecutors;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.data.repository.database.FileDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileRepository {
    private static FileRepository sInstance;
    private Context mContext;
    private AppExecutors mExecutors;

    public FileRepository(Context context, AppExecutors executors) {
        this.mContext = context;
        this.mExecutors = executors;
    }

    public static FileRepository getInstance(final Context context, final AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new FileRepository(context, appExecutors);
                }
            }
        }
        return sInstance;
    }

    ArrayList<FileModel> queryAllFileType(
            String type,
            HomeItemType itemType
    ) {

        String pdfExt = type;
        Uri documentsUri = MediaStore.Files.getContentUri("external");
        String[] docsProjection = {
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE,
        };
        return queryFilesFromDevice(documentsUri, docsProjection, pdfExt, mContext, itemType);
    }

    ArrayList<FileModel> queryFilesFromDevice(
            Uri uri,
            String[] projection,
            String selection,
            Context context, HomeItemType type
    ) {
        ArrayList<FileModel> listFile = new ArrayList<FileModel>();
        Cursor c = context.getContentResolver().query(
                uri, projection,
                selection,
                null,
                null
        );
        if (c != null) {
            while (c.moveToNext()) {
                String path = c.getString(0);
                long size = c.getLong(1);
                long timeAdded = c.getLong(2);
                String name = path.substring(path.lastIndexOf("/") + 1);
                long timeModified = c.getLong(4);
                listFile.add(
                        new FileModel(path, name, size, timeAdded, timeModified, type)

                );
            }
            c.close();
        }
        //.add(path.toString())
        return listFile;
    }

    ArrayList<FileModel> getPdfFile() {
        return queryAllFileType("_data LIKE '%.pdf'", HomeItemType.PDF);
    }

    ArrayList<FileModel> getDocFile() {
        ArrayList<FileModel> arrayList = queryAllFileType("_data LIKE '%.doc'", HomeItemType.WORD);
        arrayList.addAll(
                queryAllFileType(
                        "_data LIKE '%.docx'",
                        HomeItemType.WORD
                )
        );
        return arrayList;
    }

    ArrayList<FileModel> getPowerPointFile() {
        ArrayList<FileModel> arrayList = queryAllFileType(
                "_data LIKE '%.pptx'",
                HomeItemType.POWER_POINT
        );
        arrayList.addAll(
                queryAllFileType(
                        "_data LIKE '%.ppt'",
                        HomeItemType.POWER_POINT
                )
        );
        return arrayList;
    }

    ArrayList<FileModel> getTextFile() {
        return queryAllFileType("_data LIKE '%.txt'", HomeItemType.TEXT);
    }

    ArrayList<FileModel> getExcelFile() {
        ArrayList<FileModel> arrayList =
                queryAllFileType(
                        "_data LIKE '%.xls'",
                        HomeItemType.EXCEL
                );
        arrayList.addAll(
                queryAllFileType(
                        "_data LIKE '%.xlsx'",
                        HomeItemType.EXCEL
                )
        );
        arrayList.addAll(
                queryAllFileType(
                        "_data LIKE '%.xlsm'",
                        HomeItemType.EXCEL
                )
        );
        return arrayList;
    }

    ArrayList<FileModel> getAll() {
        ArrayList<FileModel> arrayList = getPdfFile();
        arrayList.addAll(getDocFile());
        arrayList.addAll(getPowerPointFile());
        arrayList.addAll(getExcelFile());
        arrayList.addAll(getTextFile());
        return arrayList;
    }

    ArrayList<FileModel> getAllFileImage() {
        ArrayList<FileModel> arrayList = new ArrayList<FileModel>();
        File files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        for (File file : files.listFiles()) {
            if (!file.getName().equals("imageAllView"))
                arrayList.add(
                        new FileModel(file.getPath(), file.getName(), file.length(), file.lastModified(), file.lastModified(), HomeItemType.SCREEN)
                );
        }
        return arrayList;
    }
}
