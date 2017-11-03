package com.quenDel.multiUtils.neworks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quenDel.multiUtils.R;

public class NetworkHandler {

    private Activity mContext;
    private FrameLayout parentView;
    private LinearLayout NETWORK_ERROR_LAYOUT;


    public NetworkHandler(Activity activity) {
        this.mContext = activity;
    }

    public NetworkHandler(Activity activity, FrameLayout parentView) {
        this.parentView = parentView;
        this.mContext = activity;
        NETWORK_ERROR_LAYOUT = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.layout_network_error, null);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showNetworkError(View.OnClickListener clickListener) {
        parentView.addView(NETWORK_ERROR_LAYOUT);
        ((Button) NETWORK_ERROR_LAYOUT.findViewById(R.id.btnRetry)).setOnClickListener(clickListener);
    }

    public void hideNetworkError() {
        parentView.removeView(NETWORK_ERROR_LAYOUT);
    }

    public void showAlert() {
        Toast.makeText(mContext, mContext.getResources().getString(R.string.No_connection), Toast.LENGTH_SHORT).show();
    }
}