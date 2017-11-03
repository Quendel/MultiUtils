package com.quenDel.multiUtils.imagepicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Natraj on 7/17/2017.
 */

public class QuendelImagePicker extends AppCompatActivity {

    public static int CHOOSE_PHOTO_INTENT = 101;
    public static int SELECTED_IMG_CROP = 102;
    private static int ASPECT_X = 1;
    private static int ASPECT_Y = 1;
    private static int OUT_PUT_X = 300;
    private static int OUT_PUT_Y = 300;
    private static final boolean SCALE = true;


    public static QuendelImagePicker imagePicker;
    private Activity activity;
    private Context mContext;
    public ImagePickerCallback pickerCallback;
    private File selectedImageFile;
    public Uri cropPictureUrl, selectedImageUri = null, cameraUrl = null;


    public static void openImagePicker(Activity activity, Context mContext, ImagePickerCallback pickerCallback) {

        imagePicker = new QuendelImagePicker();
        imagePicker.mContext = mContext;
        imagePicker.activity = activity;
        imagePicker.pickerCallback = pickerCallback;
        imagePicker.checkPermission();
    }

    public void checkPermission() {
        DevicePermission.CheckPermissions(activity, new PermissionCallback() {
            @Override
            public void noPermissionRequired() {
                initChooser();
            }

            @Override
            public void onPermissionGranted() {
                initChooser();
            }

            @Override
            public void onPermissionDenied() {
                showMsg("Permission Denied. Please accept all permissions");
            }
        });
    }

    private void initChooser() {
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        cameraUrl = FileUtil.getInstance(mContext).createImageUri();
        //Create any other intents you want
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUrl);

        //Add them to an intent array
        Intent[] intents = new Intent[]{cameraIntent};

        //Create a choose from your first intent then pass in the intent array
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Pic");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooserIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        ((Activity) mContext).startActivityForResult(chooserIntent, CHOOSE_PHOTO_INTENT);
    }

    public void handleGalleryResult(Intent data) {
        try {
            selectedImageFile = FileUtil.getInstance(mContext)
                    .createImageTempFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            cropPictureUrl = Uri.fromFile(selectedImageFile);

            Uri pathUri = getImageUrlWithAuthority(mContext, data.getData());
            cropImage(pathUri, cropPictureUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCameraResult(Uri cameraPictureUrl) {
        try {
            selectedImageFile = FileUtil.getInstance(mContext)
                    .createImageTempFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            cropPictureUrl = Uri.fromFile(selectedImageFile);

            cameraPictureUrl = getImageUrlWithAuthority(mContext, cameraPictureUrl);
            cropImage(cameraPictureUrl, cropPictureUrl);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public Uri getCameraUri() {
        return cameraUrl;
    }

    public Uri getCropImageUri() {
        return selectedImageUri;
    }

    public File getImageFile() {
        return selectedImageFile;
    }

    public void cropImage(final Uri sourceImage, Uri destinationImage) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setType("image/*");

        List<ResolveInfo> list = mContext.getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            //Utils.showToast(mContext, mContext.getString(R.string.error_cant_select_cropping_app));
            selectedImageUri = sourceImage;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, sourceImage);
            ((Activity) mContext).startActivityForResult(intent, SELECTED_IMG_CROP);
            return;
        } else {
            intent.setDataAndType(sourceImage, "image/*");
            intent.putExtra("aspectX", ASPECT_X);
            intent.putExtra("aspectY", ASPECT_Y);
            intent.putExtra("outputY", OUT_PUT_Y);
            intent.putExtra("outputX", OUT_PUT_X);
            intent.putExtra("scale", SCALE);

            //intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, destinationImage);
            selectedImageUri = destinationImage;
            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                ((Activity) mContext).startActivityForResult(intent, SELECTED_IMG_CROP);
            } else {
                Intent i = new Intent(intent);
                i.putExtra(Intent.EXTRA_INITIAL_INTENTS, list.toArray(new Parcelable[list.size()]));
                ((Activity) mContext).startActivityForResult(intent, SELECTED_IMG_CROP);
            }
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static Uri getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp);//.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        OUT_PUT_X = inImage.getWidth();
        OUT_PUT_Y = inImage.getHeight();
        ASPECT_X = inImage.getWidth();
        ASPECT_Y = inImage.getHeight();
        return Uri.parse(path);
    }

}


/*  Uri pathUri = data.getData();
            if (pathUri.toString().startsWith("content://com.google.android.apps.photos.contentprovider")) {
                pathUri = getImageUrlWithAuthority(mContext, data.getData());
            }*/
