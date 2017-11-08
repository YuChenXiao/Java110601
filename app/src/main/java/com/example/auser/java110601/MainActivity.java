package com.example.auser.java110601;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLData;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String>adapter;
    ArrayList<phone>mylist;
    ArrayList<String>showList;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        mylist = new ArrayList<>();
        showList=new ArrayList<>();
        DBinfo.DB_FILE = getFilesDir() + File.separator + "mydata.sqlite";
        copyDBFile();


        adapter = new ArrayAdapter<String>(MainActivity. this, android.R.layout.simple_list_item_1, showList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, DetailActivity.class);
                it.putExtra("id", mylist.get(position).id);
                startActivity(it);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //把LIST清空 在重建
        mylist.clear();
        showList.clear();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DBinfo.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
//        Cursor c = db.rawQuery("Select * from phone", null);
        //設定資料
        c =db.query("phone",new String[]{"id","username","tel"},null,null,null,null,null);
        if (c.moveToFirst()) {
            do {

                mylist.add(new phone(c.getInt(0), c.getString(1),c.getString(2)));
                showList.add(c.getString(1));
            } while (c.moveToNext());
        }

        //提醒list有新的
        adapter.notifyDataSetChanged();
    }

    ///////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add){
            Intent it =new Intent(MainActivity.this,AddActivity.class);
            startActivity(it);

        }
        return super.onOptionsItemSelected(item);
    }


    /////////////

    public void copyDBFile(){
        try {
            File f = new File(DBinfo.DB_FILE);
            if (! f.exists())
            {
                InputStream is = getResources().openRawResource(R.raw.data);
                OutputStream os = new FileOutputStream(DBinfo.DB_FILE);
                int read;
                while ((read = is.read()) != -1)
                {
                    os.write(read);
                }
                os.close();
                is.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

