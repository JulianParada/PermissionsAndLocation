package com.example.taller2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    private static final int READ_CONTACTS_ID = 1;
    private ListView contactos;
    private String[] columns;
    private Cursor cursor;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactos = findViewById(R.id.lvContacts);
        columns = new String[]{ContactsContract.Profile._ID, ContactsContract.Profile.DISPLAY_NAME_PRIMARY};
        adapter = new ContactAdapter(this, null, 0);
        contactos.setAdapter(adapter);
        solicitPermission(this, Manifest.permission.READ_CONTACTS, "Permission to Read Contacts", READ_CONTACTS_ID);
        usePermission();
    }

    private void solicitPermission(Activity context, String permit, String justification, int id){
        if(ContextCompat.checkSelfPermission(context, permit) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, permit)){
                Toast.makeText(this, justification, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permit}, id);
        }
    }

    private void usePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, columns, null, null, null);
            adapter.changeCursor(cursor);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case READ_CONTACTS_ID: {
                usePermission();
            }
        }
    }
}