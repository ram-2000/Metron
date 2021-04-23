package com.example.metron;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class image extends AppCompatActivity {
    ImageView imageView;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_image);
        //set imageView
        imageView=findViewById (R.id.image_from_device);
        //to get data from intent
        ArrayList<File> documents = (ArrayList<File>)getIntent().getSerializableExtra("documents");
        //data from file path
        Bitmap myBitmap = BitmapFactory.decodeFile(documents.get(0).getAbsolutePath());
        Bitmap rotated_bitmap=RotateBitmap (myBitmap, (float) 90.0); //rotation
//        imageView.setImageBitmap(rotated_bitmap); //to set the image
        initLoadOpenCV ();
        //to get the canny output
        Mat img = new Mat();
        Utils.bitmapToMat(rotated_bitmap, img);
        Bitmap final1 =edgesim (img);
        imageView.setImageBitmap (final1);
    }
    private  void initLoadOpenCV() {
        boolean isDebug = OpenCVLoader.initDebug();
        if (isDebug) {
            Log.i("init Opencv", "init openCV success!!");
        } else {
            Log.e("init Opencv", "init openCV failure!!");
        }
    }
    //For rotation
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix ();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap edgesim(Mat img1) {
        Bitmap ansBitmap;
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();
        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>(); //A list to store all the contours
        //Converting the image to grayscale
        Imgproc.cvtColor(img1, grayMat, Imgproc.COLOR_BGR2GRAY);

        //GAussian blur
        org.opencv.core.Size s = new Size(3,3);
        Imgproc.GaussianBlur(grayMat, grayMat, s, 2);

        //dilate and erode
        Imgproc.erode(grayMat, grayMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));

        Imgproc.dilate(grayMat, grayMat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));


        //convert to canny
        Imgproc.Canny(grayMat, cannyEdges, 10, 100);
        // find countours
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
        hierarchy.release();
        //draw countours
        Mat final_canny= new Mat();
        final_canny.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);

        Random r=new Random ();

        Log.d("abhiram",Integer.toString (contourList.size()));
        for (int i = 0; i < contourList.size(); i++) {
            Mat contour = contourList.get (i);
            double contourArea = Imgproc.contourArea (contour);
//            if (contourArea > 100) {
                Imgproc.drawContours (final_canny, contourList, i, new Scalar (r.nextInt (255),r.nextInt (255),r.nextInt (255)), 5);
//                Imgproc.boundingRect (final_canny);
//            }
        }



        //creating bitmap
        ansBitmap=Bitmap.createBitmap (final_canny.cols (), final_canny.rows (),Bitmap.Config.ARGB_8888);
        //coversion
        Utils.matToBitmap(final_canny,ansBitmap);
        //return
        return ansBitmap;
    }
}