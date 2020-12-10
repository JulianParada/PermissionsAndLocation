package com.example.taller2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.taller2.R;

public class ContactAdapter extends CursorAdapter {

    private static final int CONTACT_ID_INDEX = 0;
    private static final int DISPLAY_NAME_INDEX = 1;

    public ContactAdapter(Context context, Cursor c, int flag){
        super(context, c, flag);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.contact, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idContact = (TextView) view.findViewById(R.id.tvIdContact);
        TextView contact = (TextView) view.findViewById(R.id.tvContact);
        int id = cursor.getInt(CONTACT_ID_INDEX);
        String name = cursor.getString(DISPLAY_NAME_INDEX);
        idContact.setText(String.valueOf(id));
        contact.setText(name);
    }
}
