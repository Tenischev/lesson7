package com.example.lesson7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.lesson6.R;

import java.util.ArrayList;

public class MyActivity extends Activity {
    public static RSSItem selectedRssItem = null;
    public static Context context;
    String feedUrl = "";
    ListView rssListView = null;
    public static ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
    public static MyArrayAdapter myArrayAdapter;
    public static ProgressBar progressBar;
    public static EditText rssURLTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = getApplicationContext();

        rssURLTV = (EditText) findViewById(R.id.rssURL);
        Button fetchRss = (Button) findViewById(R.id.fetchRss);
        Button listRss = (Button) findViewById(R.id.butListRSS);
        rssListView = (ListView) findViewById(R.id.rssListView);
        if (!"".equals(rssURLTV.getText())){
            refreshNews();
        }
        myArrayAdapter = new MyArrayAdapter(this, rssItems);
        rssListView.setAdapter(myArrayAdapter);
        feedUrl = rssURLTV.getText().toString();

        listRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),ListRSS.class);
                startActivity(intent);
            }
        });

        fetchRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedUrl = rssURLTV.getText().toString();
                refreshRssList();
            }
        });

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int index,long arg3) {
                selectedRssItem = rssItems.get(index);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),RSSItemDisplayer.class);
                startActivity(intent);

            }
        });
    }

    public static void refreshNews(){
        DbNews dbNews = new DbNews(context);
        SQLiteDatabase db = dbNews.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbNews.TABLE_NAME + " WHERE " + DbNews.CHANEL + " = '" + rssURLTV.getText() + "';",null);
        cursor.moveToFirst();
        rssItems.clear();
        for (int i=0;i<cursor.getCount();i++){
            String title = cursor.getString(cursor.getColumnIndex(DbNews.TITLE));
            String descrip = cursor.getString(cursor.getColumnIndex(DbNews.DESCRIP));
            String date = cursor.getString(cursor.getColumnIndex(DbNews.DATE));
            String link = cursor.getString(cursor.getColumnIndex(DbNews.LINK));
            rssItems.add(new RSSItem(title, descrip, date, link));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

    private void refreshRssList() {
        Intent intent = new Intent(this,MyIntentServ.class);
        startService(intent.putExtra("task","fetch").putExtra("link",feedUrl));
    }
}
