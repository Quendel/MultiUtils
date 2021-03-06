package com.quenDel.multiUtils.utils;

import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.quenDel.multiUtils.R;


public class AppProgressDialog {

    public static void show(Dialog mProgressDialog) {
        try {
            if (mProgressDialog.isShowing())
                return;
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.layout_progress_bar);
            // ((TextView) mProgressDialog.findViewById(R.id.title)).setText(msg);
            mProgressDialog.setCancelable(false);
            if (mProgressDialog.getWindow() != null)
                mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mProgressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showWithTitle(Dialog mProgressDialog, String title) {
        try {
            if (mProgressDialog.isShowing())
                return;
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.layout_progress_bar);
            ((TextView) mProgressDialog.findViewById(R.id.title)).setText(title);
            mProgressDialog.setCancelable(false);
            //if (mProgressDialog.getWindow() != null)
            //mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mProgressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hide(Dialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}