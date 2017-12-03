package com.gqoha.developerx.bandl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.SocketTimeoutException;

import data.Data;
import data.DatabaseCrud;

public class Checkout extends AppCompatActivity {
TextView t;
    Pay newFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t=findViewById(R.id.total);
        newFragment = new Pay();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class ReadServer extends AsyncTask<String,Void,Double> {
        double bc;
        @Override
        protected Double doInBackground(String... params) {
         bc=DatabaseCrud.AutoRefresh(getApplicationContext());
            return bc;
        }
        // This is called when doInBackground() is finished
        protected void onPostExecute(Double result) {
            t.setText((result+""));
            Log.v("ans",result+" ");
        }




    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            new ReadServer().execute("");
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        // Unregister since the activity is about to be closed.
        super.onResume();
        sendMessage();
    }

    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public void Dismiss(View view){
        //  (CustomDialogFragment)DialogFragment.dismiss();
        try {
            Pay close = (Pay)getSupportFragmentManager().findFragmentByTag("dialog");
            close.dismiss();
        }catch (Exception e){

        }

    }
}
