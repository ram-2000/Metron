package com.example.metron;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.ArrayList;

public class image extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_image);
        imageView=findViewById (R.id.image_from_device);
        ArrayList<File> documents = (ArrayList<File>)getIntent().getSerializableExtra("documents");
        Bitmap myBitmap = BitmapFactory.decodeFile(documents.get(0).getAbsolutePath());
        Bitmap rotated_bitmap=RotateBitmap (myBitmap, (float) 90.0);
        imageView.setImageBitmap(rotated_bitmap);
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix ();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}