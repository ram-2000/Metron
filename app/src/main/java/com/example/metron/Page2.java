package com.example.metron;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Page2 extends AppCompatActivity {
    ImageButton btn;
    int REQUEST_CODE_PERMISSIONS=100;
    String[] REQUIRED_PERMISSIONS={"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
    ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ProcessCameraProvider cameraProvider;
    ImageCapture imageCapture;
    Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        btn =findViewById (R.id.cameraShutter);
        btn.setOnClickListener (v -> takePhoto());
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
}

    private void takePhoto() {
        File file= new File (getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + System.currentTimeMillis() + ".png");
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        String msg=file.getAbsolutePath ();
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                        ArrayList<File> documents= new ArrayList<File> ();
                        documents.add (file);
                        Intent intent1 =new Intent (Page2.this,image.class);
                        intent1.putExtra("documents", documents);
                        startActivity (intent1);
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException error) {
                        // insert your code here.
                        String msg="Error";
                        error.printStackTrace ();
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        PreviewView previewView=findViewById (R.id.preview);
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(previewView.getDisplay().getRotation())
                        .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview,imageCapture);
    }
    private boolean allPermissionsGranted() {
        for(String permission:REQUIRED_PERMISSIONS)
        {
            if(ContextCompat.checkSelfPermission (this,permission)== PackageManager.PERMISSION_DENIED)
            {
                requestPermissions (REQUIRED_PERMISSIONS,REQUEST_CODE_PERMISSIONS);
            }
        }
        return true;
    }
    }