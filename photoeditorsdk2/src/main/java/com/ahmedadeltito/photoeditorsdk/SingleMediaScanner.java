package com.ahmedadeltito.photoeditorsdk;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mMs;
    private File mFile;
    private ScanFinish mScanFinish;

    public SingleMediaScanner(Context context, File f, ScanFinish mScanFinish) {
        this.mScanFinish = mScanFinish;
        mFile = f;
        mMs = new MediaScannerConnection(context, this);
        mMs.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMs.scanFile(mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMs.disconnect();
        mScanFinish.finishScan(true);
    }


    public interface ScanFinish{
        void finishScan(Boolean success);
    }
}