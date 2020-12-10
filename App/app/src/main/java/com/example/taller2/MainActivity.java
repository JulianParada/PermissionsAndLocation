package com.example.taller2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibPermits, ibAccess, ibMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibPermits = findViewById(R.id.ivPermits);
        ibAccess = findViewById(R.id.ivAccess);
        ibMaps = findViewById(R.id.ivMaps);

        ibPermits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });

        ibAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        ibMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}