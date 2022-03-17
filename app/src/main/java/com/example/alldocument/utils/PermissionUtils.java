package com.example.alldocument.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PermissionUtils {
    public static int ID_PERMISSIONS_REQUEST = 1000;
    public static int ID_PERMISSIONS_REQUEST_R = 2000;
    private static String[] listPermission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void requestPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivityForResult(intent, ID_PERMISSIONS_REQUEST_R);
        } else {
            if (!checkPermission(context)) {
                ActivityCompat.requestPermissions(context, listPermission, ID_PERMISSIONS_REQUEST);
            }
        }

    }

    public static boolean checkPermission(Context context) {
        boolean isOn = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                isOn = true;
            } else {
                isOn = false;
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == 0) {
                isOn = true;
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0) {
                isOn = true;
            }
        }
        return isOn;
    }

    public static String getSizeString(long paramDouble) {
        double d1 = paramDouble / 1024.0D;
        double d2 = d1 / 1024.0D;
        double d3 = d2 / 1024.0D;
        if (paramDouble < 1024.0D) {
            return paramDouble + " bytes";
        }
        if (d1 < 1024.0D) {
            return new BigDecimal(d1).setScale(2, 4).toString() + " KB";
        }
        if (d2 < 1024.0D) {
            return new BigDecimal(d2).setScale(2, 4).toString() + " MB";
        }
        return new BigDecimal(d3).setScale(2, 4).toString() + " GBs";
    }

    public static String dateFormat(long time) {
        Date date = new Date(time * 1000);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }
}
