package com.quenDel.multiUtils.imagepicker;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;

/**
 * Created by Natraj on 7/17/2017.
 */

public class DevicePermission extends AppCompatActivity {
    private Activity activity;
    public static PermissionCallback permissionCallback;
    public static final int REQUEST_CODE = 999;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private ArrayList<String> PENDING_PERMISSION = new ArrayList<>();

    public static void CheckPermissions(Activity activity, PermissionCallback permissio) {
        DevicePermission devicePermission = new DevicePermission();
        permissionCallback = permissio;
        devicePermission.activity = activity;
        devicePermission.permission();
    }

    private void permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    PENDING_PERMISSION.add(permission);
                }
            }
            if (!PENDING_PERMISSION.isEmpty()) {
                ActivityCompat.requestPermissions(activity, PENDING_PERMISSION.toArray(new String[PENDING_PERMISSION.size()]), REQUEST_CODE);
            } else {
                permissionCallback.onPermissionGranted();
            }
        } else {
            permissionCallback.noPermissionRequired();
        }
    }

    public static boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
