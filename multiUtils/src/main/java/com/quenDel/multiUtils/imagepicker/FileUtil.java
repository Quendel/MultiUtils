package com.quenDel.multiUtils.imagepicker;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Natraj on 7/17/2017.
 */

public class FileUtil {

    private static FileUtil sSingleton;
    private Context context;

    private FileUtil(Context ctx) {
        context = ctx;
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static FileUtil getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (FileUtil.class) {
                sSingleton = new FileUtil(ctx);
            }
        }
        return sSingleton;
    }

    public Uri createImageUri() {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, timeStamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }

    /**
     * Create image temp file file.
     *
     * @param filePathDir the file path dir
     * @return the file
     * @throws IOException the io exception
     */
    @SuppressLint("SimpleDateFormat")
    public File createImageTempFile(File filePathDir) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                filePathDir      /* directory */
        );
    }

    public static String getUploadFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        Date date = new Date();
        return String.format("profile_%s.png", sdf.format(date));
    }
}