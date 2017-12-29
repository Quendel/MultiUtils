package com.example.natraj.fingerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.quenDel.multiUtils.imagepicker.ImagePickerCallback;
import com.quenDel.multiUtils.imagepicker.QuendelImagePicker;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private QuendelImagePicker quendelImagePicker;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });


        quendelImagePicker = QuendelImagePicker.mInstance();
        quendelImagePicker.setPictureQuality(70, 200, 200,
                200, 200, false);

    }


    private void pickImage() {
        quendelImagePicker.openImagePicker(this, mContext, new ImagePickerCallback() {
            @Override
            public void onImagePicked(File imageFile) {
                Log.e("imageFile ", imageFile.toString());
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.fromFile(imageFile));
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case QuendelImagePicker.CHOOSE_PHOTO_INTENT:
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        if (data != null && data.getData() != null) {
                            quendelImagePicker.handleGalleryResult(data);
                        } else {
                            quendelImagePicker.handleCameraResult(quendelImagePicker.getCameraUri());
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case QuendelImagePicker.SELECTED_IMG_CROP:
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        quendelImagePicker.pickerCallback.onImagePicked(quendelImagePicker.getImageFile());
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
    }
}
