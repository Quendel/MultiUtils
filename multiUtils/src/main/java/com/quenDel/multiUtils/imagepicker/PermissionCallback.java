package com.quenDel.multiUtils.imagepicker;

/**
 * Created by Natraj on 7/17/2017.
 */

public interface PermissionCallback {
    void noPermissionRequired();

    void onPermissionGranted();

    void onPermissionDenied();
}
