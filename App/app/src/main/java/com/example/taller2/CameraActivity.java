package com.example.taller2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_REQUEST = 4;
    private static final int CAMERA = 2;
    private static final int ALMACENAMIENTO_EXTERNO = 3;
    private static boolean accessCamera = false, accessAlm = false;
    private ImageView ivImage;
    private Button btnImage, btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ivImage = findViewById(R.id.ivImage);
        btnImage = findViewById(R.id.btnImagen);
        btnCamera = findViewById(R.id.btnCamara);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessAlm = solicitPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, "Permission to Access Gallery", ALMACENAMIENTO_EXTERNO);
                if(accessAlm){
                    usePermissionImage();
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessCamera = solicitPermission(CameraActivity.this, Manifest.permission.CAMERA, "Permission to Access Camera", CAMERA);
                if(accessCamera){
                    usePermissionCamera();
                }
            }
        });
    }

    private boolean solicitPermission(Activity context, String permit, String justification, int id){
        if(ContextCompat.checkSelfPermission(context, permit) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, permit)){
                Toast.makeText(this, justification, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permit}, id);
            return false;
        } else {
            return true;
        }
    }

    private void usePermissionCamera(){
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void usePermissionImage(){
        Intent pictureIntent = new Intent(Intent.ACTION_PICK);
        pictureIntent.setType("image/*");
        startActivityForResult(pictureIntent, IMAGE_PICKER_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    usePermissionCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Access denied to camera", Toast.LENGTH_LONG).show();
                }
                break;
            }

            case ALMACENAMIENTO_EXTERNO: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    usePermissionImage();
                } else {
                    Toast.makeText(getApplicationContext(), "Access denied to image gallery", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE: {
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ivImage.setImageBitmap(imageBitmap);
                }
                break;
            }
            case IMAGE_PICKER_REQUEST: {
                if(resultCode == RESULT_OK){
                    try{
                        final Uri imageUri = data.getData();
                        final InputStream is = getContentResolver().openInputStream(imageUri);
                        final Bitmap selected = BitmapFactory.decodeStream(is);
                        ivImage.setImageBitmap(selected);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}