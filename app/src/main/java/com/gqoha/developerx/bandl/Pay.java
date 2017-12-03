package com.gqoha.developerx.bandl;

/**
 * Created by DeveloperX on 25-Nov-17.
 */

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import data.DatabaseCrud;


/**
 * Created by DevelopX on 2016-07-22.
 */
public class Pay extends DialogFragment implements AdapterView.OnItemSelectedListener {

Spinner SP;
Button SEND;
static String food_package;
String deliver_option;
String cashOrM;
String timex;
    View rootView;
    CompoundButton mCash;
    CompoundButton mMontly;

    CompoundButton Pickup;//Or deliver


    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.residence, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        timex =new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        rootView = inflater.inflate(R.layout.pay_pop, container, false);
        SP=rootView.findViewById(R.id.spinner2);
        SEND=rootView.findViewById(R.id.button);

        mCash=rootView.findViewById(R.id.cash);
         mMontly=rootView.findViewById(R.id.month);
        Pickup=rootView.findViewById(R.id.deliver);

        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCash.isChecked()||mMontly.isChecked()) {//
                new ReadServer().execute("http://bh.jobl.co.za/result.php?d=" + DatabaseCrud.replaceWhite(deliver_option) + "&c_m=" + cash_or() + "&time=" + timex);
                    Pay close = (Pay)getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                    close.dismiss();
                    Toast toast = Toast.makeText(getActivity(), "Sending the Order",Toast.LENGTH_SHORT );
                    toast.show();
                  }
                  else{
                    Snackbar.make(view, "Select Cash or Montly subscription", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }

        });

        mCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    String cashOrM="Cash";
                    mMontly.setChecked(false);//a
                }
                else{
                    //mCash.setChecked(false);//a

                    String cashOrM="Monthly";
                }
            }
        });

        mMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    String cashOrM="Monthly";
                    mCash.setChecked(false);
                    //mMontly.setChecked(false);
                }
                else{
                    String cashOrM="Cash";
                }
            }
        });

        Pickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                   SP.setVisibility(View.GONE);
                    deliver_option="Pickup";
                }
                else{
                    Pickup.setChecked(false);
                    SP.setVisibility(View.VISIBLE);
                }

            }
        });


        SP.setAdapter(adapter);
        SP.setOnItemSelectedListener(this);
        return rootView;
    }

    public String cash_or(){

        return mCash.isChecked()? "Cash":"Monthly";
    }


    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Hi");
        // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Pickup.setChecked(false);
        deliver_option = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private String loadJsonFromNetwork(String urlString) throws IOException {
       // Log.v(LOG,urlString);
        URL url =new URL(urlString);
        String JsonString =null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();


            if (inputStream == null) {
                // Nothing to do.
                JsonString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                JsonString = null;
            }
            JsonString = buffer.toString();


        }catch (IOException e){
            e.getMessage();
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return JsonString;//returns JSON
    }




    public class ReadServer extends AsyncTask<String,Void,Void> {
        String bc=" ";
        @Override
        protected Void doInBackground(String... params) {
            food_package= DatabaseCrud.SendOrder(getActivity());

            String fileUrl = params[0]+"&fp="+food_package;

            try{
                Log.v("Pay",fileUrl);
                bc=loadJsonFromNetwork(fileUrl);
//          Log.v("Pay",bc);
            } catch (SocketTimeoutException e){

            } catch (IOException e){

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
                        //DatabaseCrud.ToAaddMenu(getActivity(),name,decri,price,id,rest,link);
                    }
                }else{

                }
            }catch (Exception e){
                //Log.v(LOG,list.size()+"Error,exception"+e.getMessage());
            }

            return null;
        }
        // This is called when doInBackground() is finished
        protected void onPostExecute(String result) {

        }

    }
   // android:onClick="DismissPay"
}

