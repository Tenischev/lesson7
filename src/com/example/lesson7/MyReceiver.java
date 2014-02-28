package com.example.lesson7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: kris13
 * Date: 28.02.14
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        CharSequence text = "Loading successful";
        int duration = Toast.LENGTH_SHORT;

        MyActivity.myArrayAdapter.notifyDataSetChanged();
        MyActivity.refreshNews();

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
