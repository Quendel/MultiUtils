package com.quenDel.multiUtils.utils;

/**
 * Created by Natraj on 7/29/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;



/**
 * Created by Lenovo on 04/06/2017.
 */

public class MyImageLoader {
    ProgressBar progressBar;
/*
    public MyImageLoader(Context context, String imageUri, ImageView imageView, ProgressBar progressBar) {
        this.progressBar = progressBar;
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instan
        imageLoader.init(ImageLoaderConfiguration.createDefault((context)));

        imageLoader.displayImage(imageUri, imageView, getImageOption(), listener, new
                ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {

                    }
                });
    }


    ImageLoadingListener listener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {
            progressBar.setVisibility(View.GONE);
        }
    };

    public static DisplayImageOptions getImageOption() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder) // resource or drawable
                .showImageForEmptyUri(R.drawable.place_holder) // resource or drawable
                .showImageOnFail(R.drawable.no_image_placeholder) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(0)
                .cacheInMemory(false) // default
                .cacheOnDisk(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();
    }*/
}