package com.example.auser.java110601;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void clickOK(View v){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        EditText ed = (EditText)findViewById(R.id.editText);
        EditText ed2 = (EditText)findViewById(R.id.editText2);
        String username = ed.getText().toString();
        String tel = ed2.getText().toString();
        String str = "Insert Into phone (username, tel) values ('" + username + "', '" + tel + "')";
        db.execSQL(str);
        finish();
    }

    public void clickNO(View v){
        finish();
    }


}
