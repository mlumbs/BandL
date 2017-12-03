package com.gqoha.developerx.bandl;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import Adapters.MenuAdapter;
import Adapters.RestaAdapter;
import data.Data;
import data.DatabaseCrud;
import data.JobContracts;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenusFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    FloatingActionButton fab;
    public static final String LOG = "Menu Server";
    private static final int DETAIL_LOADER = 0;
    private Uri mUri;
    private MenuAdapter adapter;
    String db;
    public static final String[] JOB_COLUMNS = {
            JobContracts.Menu._ID ,
            JobContracts.Menu.COLUMN_Name_Food,
            JobContracts.Menu.COLUMN_Restarent,
            JobContracts.Menu.COLUMN_Price,
            JobContracts.Menu.COLUMN_EntryID,
            JobContracts.Menu.COLUMN_TAG,
    };

    public static final int COL_ID = 0;  //ID
    public static final int COL_NAME = 1;//Name
    public static final int COL_RESTA= 2; //resta
    public static final int COL_PRICE=3;//price;
    public  static final int COL_ENTRY= 4;//id_entry
    public static final int COL_TAG= 5; //imagelink
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public MenusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //return inflater.inflate(R.layout.fragment_menus, container, false);
        sharedPref = getActivity().getSharedPreferences(
                Data.PREF_FILE, Context.MODE_PRIVATE);
        db=sharedPref.getString(Data.LAST_ENTRY,"1");

        fab =getActivity().findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new ReadServer().execute(db);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_menus, container, false);
        adapter = new MenuAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;

    }

    public void onResume () {
        super.onResume();
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            //   Log.v("Job onCreateLoader", "mUri is"+mUri);
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    JobContracts.Menu.CONTENT_URI,
                    JOB_COLUMNS,
                    null,
                    null,
                    null
            );
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private String loadJsonFromNetwork(int urlString) throws IOException {

if(urlString==1){
    return "{\"entries\":[{\"n_f\":\"small burgers\",\"p\":\"23\",\"d\":\"burger and grained onion \",\"image_path\":\"aa\",\"id\":\"2\",\"r\":\"100\"},{\"n_f\":\"Fast Breakfast\",\"p\":\"17\",\"d\":\"Bread slices ,sausages and tea\",\"image_path\":\"ab\",\"id\":\"3\",\"r\":\"100\"},{\"n_f\":\"Big Burger King \",\"p\":\"25\",\"d\":\"burger and coca cola 500 ml\",\"image_path\":\"ac\",\"id\":\"4\",\"r\":\"200\"},{\"n_f\":\"Burger And Chips\",\"p\":\"52\",\"d\":\"Burger,Chips and slalad \",\"image_path\":\"ad\",\"id\":\"7\",\"r\":\"300\"},{\"n_f\":\"Fresh Beef \",\"p\":\"91\",\"d\":\"grilled beef and cheese\",\"image_path\":\"ae\",\"id\":\"8\",\"r\":\"300\"},{\"n_f\":\"Apple\",\"p\":\"2\",\"d\":\"fresh apples\",\"image_path\":\"af\",\"id\":\"9\",\"r\":\"100\"}]}";
}
        return null;//returns JSON
    }

    public class ReadServer extends AsyncTask<String,Void,Void> {
        String bc;
        @Override
        protected Void doInBackground(String... params) {
            String fileUrl = params[0];
            Log.v(LOG,"Call Timer now");
            try{
                Log.v(LOG,"try entered");
                bc=loadJsonFromNetwork(Integer.parseInt(fileUrl));

            } catch (SocketTimeoutException e){
                Log.v(LOG,"Time out");
            } catch (IOException e){
                Log.v(LOG,"Time out");
            }

            try {

                JSONObject Json = new JSONObject(bc); //JsonDe offline
                if (!Json.isNull("entries"))
                {
                    Log.v("Size", "This is not null");
                    JSONArray JArray = Json.getJSONArray("entries");//This return null if network is unavailable
                    for (int z = 0; z < JArray.length(); z++) {
                        String name = JArray.getJSONObject(z).getString("n_f");
                        String decri = JArray.getJSONObject(z).getString("d");
                        String price = JArray.getJSONObject(z).getString("p");
                        String id = JArray.getJSONObject(z).getString("id");
                        String rest = JArray.getJSONObject(z).getString("r");
                        String link = JArray.getJSONObject(z).getString("image_path");
                        DatabaseCrud.ToAaddMenu(getActivity(),name,decri,price,id,rest,link);
                    }
                }else{

                }
            }catch (Exception e){
                //Log.v(LOG,list.size()+"Error,exception"+e.getMessage());
            }

            editor =sharedPref.edit();
            editor.putString(Data.LAST_ENTRY,"2");
            // editor.putString(Data.LAST_ENTRY,LastID);
            editor.apply();



            return null;
        }
        // This is called when doInBackground() is finished
        protected void onPostExecute(String result) {

        }

    }


}
