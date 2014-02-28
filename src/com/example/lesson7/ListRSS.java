package com.example.lesson7;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.lesson6.R;

import java.util.ArrayList;

public class ListRSS extends Activity {
    private static ArrayList<String> rssURL = new ArrayList<String>();
    public static ArrayAdapter<String> aa = null;
    private ListView listRss;
    ListRSS instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rss);

        instance = this;
        listRss = (ListView) findViewById(R.id.listRss);
        Button add = (Button) findViewById(R.id.add);
        refreshList(instance);
        aa = new ArrayAdapter<String>(this,R.layout.list_item,R.id.label,rssURL);
        listRss.setAdapter(aa);

        listRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int index,long arg3) {
                String select = rssURL.get(index);
                DbListRSS dbList = new DbListRSS(instance);
                SQLiteDatabase db = dbList.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + DbListRSS.TABLE_NAME + " WHERE " + DbListRSS.NAME + " = '" + select + "';",null);
                cursor.moveToFirst();
                if (cursor.getCount() > 0){
                    select = cursor.getString(cursor.getColumnIndex(DbListRSS.ADRES));
                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                MyActivity.rssURLTV.setText(select);
                MyActivity.myArrayAdapter.notifyDataSetChanged();
                MyActivity.refreshNews();
                close();
            }
        });

        listRss.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String select = rssURL.get(i);
                DbListRSS dbListRSS = new DbListRSS(MyActivity.context);
                SQLiteDatabase db = dbListRSS.getWritableDatabase();
                dbListRSS.onCreate(db);
                db.execSQL("DELETE FROM " + DbListRSS.TABLE_NAME + " WHERE " + DbListRSS.NAME + " = '" + select + "';");
                dbListRSS.close();

                refreshList(instance);
                aa.notifyDataSetChanged();

                return true;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddRSSToList.class);
                startActivity(intent);
            }
        });
    }

    public static void refreshList(Activity activity) {
        DbListRSS dbList = new DbListRSS(activity);
        SQLiteDatabase db = dbList.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbListRSS.TABLE_NAME,null);
        cursor.moveToFirst();
        rssURL.clear();
        for (int i=0;i<cursor.getCount();i++){
            String name = cursor.getString(cursor.getColumnIndex(DbListRSS.NAME));
            rssURL.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

    private void close(){
        super.onBackPressed();
    }
}
