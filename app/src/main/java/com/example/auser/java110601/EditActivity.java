package com.example.auser.java110601;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText ed3,ed4;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ed3=(EditText)findViewById(R.id.editText3);
        ed4=(EditText)findViewById(R.id.editText4);
        Intent it = getIntent();
        id = it.getIntExtra("id", -1);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone", new String[]{"id", "username", "tel"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) {

            ed3.setText(c.getString(1));
            ed4.setText(c.getString(2));
        }
    }
    public void clickupdata(View v){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("username",ed3.getText().toString());
        cv.put("tel",ed4.getText().toString());
        db.update("phone",cv,"id=?",new String[]{String.valueOf(id)});
        db.close();
        finish();
    }
    public void clickNO(View v){
        finish();
    }

}
