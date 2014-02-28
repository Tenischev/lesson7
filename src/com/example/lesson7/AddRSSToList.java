package com.example.lesson7;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lesson6.R;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 02.02.14
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class AddRSSToList extends Activity {
    AddRSSToList instance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rss);

        instance = this;
        Button add = (Button) findViewById(R.id.buttonadd);
        final EditText name = (EditText) findViewById(R.id.nameRSS);
        final EditText adres = (EditText) findViewById(R.id.adresRSS);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbListRSS dbPic = new DbListRSS(instance);
                SQLiteDatabase db = dbPic.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(DbListRSS.NAME,name.getText().toString());
                cv.put(DbListRSS.ADRES,adres.getText().toString());
                db.insert(DbListRSS.TABLE_NAME,null,cv);
                db.close();
                ListRSS.aa.notifyDataSetChanged();
                ListRSS.refreshList(instance);
                close();
            }
        });
    }
    private void close(){
        super.onBackPressed();
    }
}
