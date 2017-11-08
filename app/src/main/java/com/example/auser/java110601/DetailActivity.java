package com.example.auser.java110601;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    int id;
    TextView tvid,tvname,tvtel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvid = (TextView) findViewById(R.id.textView);
        tvname = (TextView) findViewById(R.id.textView2);
        tvtel = (TextView) findViewById(R.id.textView3);
        Intent it = getIntent();
        id = it.getIntExtra("id", -1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("phone", new String[]{"id", "username", "tel"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) {
            tvid.setText(String.valueOf(c.getInt(0)));
            tvname.setText(c.getString(1));
            tvtel.setText(c.getString(2));
        }
    }

    public void clickBack(View v){

        finish();
    }
    //刪除的對話框
        public void clickDelete(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("刪除確認");
            builder.setMessage("請確認刪除");
            builder.setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
                    db.delete("phone", "id=?", new String[] {String.valueOf(id)});
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        public void clickpin(View v){
            {
                Intent it =new Intent(this,EditActivity.class);
                it.putExtra("id",id);
                startActivity(it);
            }

        }
}
